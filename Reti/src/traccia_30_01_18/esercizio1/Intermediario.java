package traccia_30_01_18.esercizio1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Intermediario {

    private static ArrayList<InetAddress> indirizzi = new ArrayList<>();
    private static int idIntermediario=1;

    public Intermediario(ArrayList<Venditore> venditori){
        try {
            for (Venditore v : venditori) {
                InetAddress address = InetAddress.getByName(v.getHost());
                indirizzi.add(address);
            }
        }catch (Exception e){}
    }


    public void avvia(){
        try{
            ServerSocket ss = new ServerSocket(2345);
            while(true){
                new RequestHandler(ss.accept()).start();
            }

        }catch(Exception e){
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
                Richiesta richiesta = (Richiesta) ois.readObject();

                String risposta = inoltraRichiesta(richiesta);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(risposta);

            }catch(Exception e){
            }
        }

        private String inoltraRichiesta(Richiesta r){
            try{
                DatagramSocket ds = new DatagramSocket();
                ds.setSoTimeout(60000);
                DatagramPacket dp = null;
                byte[] buff = r.toString().getBytes();
                for(InetAddress a : indirizzi) {
                    dp = new DatagramPacket(buff,buff.length,a,6789);
                    ds.send(dp);
                }
                long start = System.currentTimeMillis();

                double prezzoMin = 0;
                while(System.currentTimeMillis()-start < TimeUnit.MINUTES.toMillis(1)) { //tempo 1 minuto
                    ds.receive(dp); //se non riceve nulla viene gestito tutto nella catch
                    String risposta = new String(dp.getData());
                    StringTokenizer st = new StringTokenizer(risposta,"<>,");
                    int idProdotto = Integer.parseInt(st.nextToken());
                    int quantita = Integer.parseInt(st.nextToken());
                    double prezzoTotale = Double.parseDouble(st.nextToken());
                    int idIntermediario = Integer.parseInt(st.nextToken());
                    if(prezzoTotale < prezzoMin || prezzoMin==0) {
                        prezzoMin = prezzoTotale;
                    }
                }

                return "<"+r.getIdProdotto()+","+r.getQuantita()+","+prezzoMin+","+idIntermediario+">";

            }
            catch(SocketTimeoutException s){
                return "<"+r.getIdProdotto()+","+r.getQuantita()+",-1,-1>";
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static void main(String[] args) {
        new Intermediario(new ArrayList<>()).avvia();
    }


}
