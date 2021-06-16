package traccia_19_06_20.esercizio2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Banco {

    private static ArrayList<Biglietto> bigliettiVenduti = new ArrayList<>();
    private static HashMap<String,Lotteria> lotterie = new HashMap<>();

    public static void avvia(){
        try{
            ServerSocket ss = new ServerSocket(3000);
            Lotteria lotteria1 = new Lotteria("lotteria1");
            Lotteria lotteria2 = new Lotteria("lotteria2");
            lotterie.put("lotteria1",lotteria1);
            lotterie.put("lotteria2",lotteria2);
            while(lotteria1.isAperta() || lotteria2.isAperta())
                new RequestHandler(ss.accept()).start();

            int vincitore = (int) (Math.random()*bigliettiVenduti.size());
            Biglietto b = bigliettiVenduti.get(vincitore);

            byte[] buff = (b.getLotteria()+","+b.getNumero()).getBytes();
            InetAddress group = InetAddress.getByName("230.0.0.1");
            DatagramPacket dp = new DatagramPacket(buff,buff.length,group,4000);
            new MulticastSocket().send(dp);

        } catch(Exception e){
        }
    }

    private static class RequestHandler extends Thread {
        private Socket socket;

        public RequestHandler(Socket s){
            socket = s;
        }

        public void run(){
            try{
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String richiesta = (String) ois.readObject();
                StringTokenizer st = new StringTokenizer(richiesta,",");
                String lotteria  = st.nextToken();
                int quantita = Integer.parseInt(st.nextToken());

                ArrayList<Biglietto> biglietti = lotterie.get(lotteria).acquista(quantita);
                for(Biglietto b : biglietti)
                    if(b.getNumero()!=0)
                        bigliettiVenduti.add(b);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(biglietti);

            }catch (Exception e){

            }
        }

    }

}
