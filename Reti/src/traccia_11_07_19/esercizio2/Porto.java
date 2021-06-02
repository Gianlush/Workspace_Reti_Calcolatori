package traccia_11_07_19.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

public class Porto {

    private static int tcpRichieste = 3000;
    private static int tcpInoltro = 4000;
    private static int udpPort = 5000;
    private static List<Boolean> banchineGrandi = Collections.synchronizedList(new ArrayList<>());
    private static List<Boolean> banchinePiccole= Collections.synchronizedList(new ArrayList<>());
    private static List<InetAddress> operatoriGrandi= Collections.synchronizedList(new ArrayList<>());
    private static List<InetAddress> operatoriPiccoli= Collections.synchronizedList(new ArrayList<>());

    private static List<InetAddress> coda= Collections.synchronizedList(new ArrayList<>());

    private static class RequestHandler extends Thread {

        private Socket socketNave;

        private RequestHandler(Socket socket){
            this.socketNave =socket;
        }

        public void run(){
            try{
                ObjectInputStream objectInputStream = new ObjectInputStream(socketNave.getInputStream());
                String richiesta = (String) objectInputStream.readObject();

                StringTokenizer stringTokenizer = new StringTokenizer(richiesta,"<>,");
                int idNave = Integer.parseInt(stringTokenizer.nextToken());
                int lunghezzaNave = Integer.parseInt(stringTokenizer.nextToken());
                int numContainer = Integer.parseInt(stringTokenizer.nextToken());
                InetAddress addressNave = socketNave.getInetAddress();

                if(lunghezzaNave<40)
                    cercaBanchina(addressNave,banchinePiccole,operatoriPiccoli);
                else
                    cercaBanchina(addressNave,banchineGrandi,operatoriPiccoli);


            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void cercaBanchina(InetAddress nave, List<Boolean> banchine, List<InetAddress> operatori) throws IOException, InterruptedException, ClassNotFoundException {

            while(!banchine.contains(true)) {
                if(!coda.contains(nave))
                    coda.add(nave);
                sleep(3000);
            }
            coda.remove(nave);

            for(int i=0;i<banchine.size();i++){
                if(banchine.get(i)){
                    banchine.set(i,false);
                    notificaOperatoreENave(i,nave,operatori.get(i));
                    banchine.set(i,true);
                    return;
                }
            }
        }

        private void notificaOperatoreENave(int numero,InetAddress nave, InetAddress address) throws IOException, ClassNotFoundException {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketNave.getOutputStream());
            objectOutputStream.writeObject(""+numero);


            Socket operatore = new Socket(address,tcpInoltro);
            objectOutputStream = new ObjectOutputStream(operatore.getOutputStream());
            objectOutputStream.writeObject(""+nave);

            ObjectInputStream objectInputStream = new ObjectInputStream(operatore.getInputStream());
            objectInputStream.readObject(); //legge ok
        }


    }

    private static class CodaHandler extends Thread {

        private DatagramSocket datagramSocket;

        public CodaHandler(DatagramSocket datagramSocket){
            this.datagramSocket = datagramSocket;
        }

        public void run(){
            try{
                while(true){
                    sleep(15000);

                    if(coda.isEmpty())
                        continue;

                    byte[] buff = ("avete rotto il cazzo").getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
                    datagramPacket.setPort(udpPort);
                    for(InetAddress address : coda){
                        datagramPacket.setAddress(address);
                        datagramSocket.send(datagramPacket);
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void avvia(){
        try{
            ServerSocket serverSocket = new ServerSocket(tcpRichieste);
            DatagramSocket datagramSocket = new DatagramSocket();
            new CodaHandler(datagramSocket).start();
            while (true) {
                Socket nave = serverSocket.accept();
                new RequestHandler(nave).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
