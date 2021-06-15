package traccia_15_01_20;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Gestore {

    private static int tcpClientPort = 1111;
    private static int tcpCentroBPort = 3333;
    private static int udpPort = 2222;
    private static String mCastAddress = "224.3.2.1";

    private static class RequestHandler extends Thread {

        private Socket client;
        private ServerSocket serverCentroBSocket;
        private MulticastSocket multicastSocket;
        private InetAddress mCastGroup;

        public RequestHandler(Socket socket, MulticastSocket multicastSocket, InetAddress address, ServerSocket serverCentroBSocket){
            this.client =socket;
            this.mCastGroup=address;
            this.multicastSocket=multicastSocket;
            this.serverCentroBSocket=serverCentroBSocket;
        }

        public void run(){
            try{
                while(true){

                    ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                    String richiesta = (String) objectInputStream.readObject();
                    System.out.println("Un client ha inviato la richiesta: "+richiesta);

                    byte[] msg = richiesta.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length,mCastGroup,udpPort);
                    multicastSocket.send(datagramPacket);
                    System.out.println("Richiesta inoltrata ai centriBenessere.");


                    serverCentroBSocket.setSoTimeout(7000);
                    StringBuilder offerte = new StringBuilder();
                    Socket centroSocket = null;

                    while(true) {
                        try {
                            centroSocket = serverCentroBSocket.accept();
                            objectInputStream = new ObjectInputStream(centroSocket.getInputStream());
                            String offertaN = (String) objectInputStream.readObject();
                            offerte.append(offertaN + ",");

                        } catch (SocketTimeoutException e){
                            break;
                        }
                    }
                    System.out.println("terminata ricezione offerte");


                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                    objectOutputStream.writeObject(offerte.toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
    public static void avvia(){
        try {
            InetAddress mCastGroup = InetAddress.getByName(mCastAddress);
            ServerSocket serverCentroSocket = new ServerSocket(tcpCentroBPort);
            MulticastSocket multicastSocket = new MulticastSocket();
            ServerSocket serverClientSocket = new ServerSocket(tcpClientPort);
            while(true) {
                Socket client = serverClientSocket.accept();
                new RequestHandler(client,multicastSocket,mCastGroup,serverCentroSocket).start();

            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Gestore.avvia();
    }

}
