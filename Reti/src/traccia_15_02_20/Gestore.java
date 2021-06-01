package traccia_15_02_20;

import traccia_11_11_20.esercizio2.Server;

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

        private ServerSocket serverClientSocket;
        private ServerSocket serverCentroBSocket;
        private MulticastSocket multicastSocket;
        private InetAddress mCastGroup;

        public RequestHandler(ServerSocket socket, MulticastSocket multicastSocket, InetAddress address, ServerSocket serverCentroBSocket){
            this.serverClientSocket =socket;
            this.mCastGroup=address;
            this.multicastSocket=multicastSocket;
            this.serverCentroBSocket=serverCentroBSocket;
        }

        public void run(){
            try{
                while(true){

                    Socket clientSocket = serverClientSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
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


                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    objectOutputStream.writeObject(offerte.toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
    public static void avvia(){
        try {
            ServerSocket serverClientSocket = new ServerSocket(tcpClientPort);
            ServerSocket serverCentroSocket = new ServerSocket(tcpCentroBPort);
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress mCastGroup = InetAddress.getByName(mCastAddress);

            new RequestHandler(serverClientSocket,multicastSocket,mCastGroup,serverCentroSocket).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Gestore.avvia();
    }

}
