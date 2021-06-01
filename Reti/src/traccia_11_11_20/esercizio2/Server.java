package traccia_11_11_20.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Server {
    private static int tcpPort = 1111;
    private static int udpPort = 4000;
    private static String group = "230.0.0.1";
    private static int nAnalisiAttive = 0;
    private static List<Double> valoriDiX = Collections.synchronizedList(new ArrayList<>());

    private static class RequestHandler extends Thread {

        private ServerSocket serverSocket;

        public RequestHandler(ServerSocket serverSocket){
            this.serverSocket = serverSocket;
        }

        public void run(){
            try{
                while(true){
                    Socket socket = serverSocket.accept();
                    boolean ok = aggiungiRichiesta();
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String analisi = (String) objectInputStream.readObject();
                    System.out.println("Ricevuta richiesta.");

                    Tupla risposta = null;
                    if(ok)
                        risposta = processaStringa(analisi);
                    else
                        risposta = new Tupla(-1,-1);

                    TimeUnit.SECONDS.sleep((long) (Math.random()*10));

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(risposta);
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public Tupla processaStringa(String analisi){
            StringTokenizer stringTokenizer = new StringTokenizer(analisi,"<>#");
            String nomeAnalisi = stringTokenizer.nextToken();
            double X = Double.parseDouble(stringTokenizer.nextToken());
            double Y = Double.parseDouble(stringTokenizer.nextToken());

            double retX = Math.sqrt(X);
            double retY = Math.pow(Y,2);
            return new Tupla(retX,retY);
        }

        public synchronized boolean aggiungiRichiesta(){
            if(nAnalisiAttive<10)
                nAnalisiAttive++;
            else
                return false;
            return true;

        }
    }

    public static class PeriodicSendThread extends Thread {

        private MulticastSocket multicastSocket;
        private InetAddress group;

        public PeriodicSendThread(MulticastSocket multicastSocket, InetAddress group){
            this.multicastSocket =multicastSocket;
            this.group=group;
        }

        public void run(){
            try{
                while (true){
                    sleep(15000);
                    double media = calcolaMedia();
                    byte[] buff = (""+media).getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length,group,udpPort);
                    multicastSocket.send(datagramPacket);
                    System.out.println("Invio periodico della media.");
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        public double calcolaMedia(){
            double media = 0;
            for(double d  : valoriDiX)
                media+=d;
            return media/valoriDiX.size();
        }
    }



    public static void avvia() {
        try {
            ServerSocket serverSocket = new ServerSocket(tcpPort);
            new RequestHandler(serverSocket).start();

            InetAddress address = InetAddress.getByName(group);
            MulticastSocket multicastSocket = new MulticastSocket();
            new PeriodicSendThread(multicastSocket,address).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server.avvia();
    }
}
