package traccia_26_02_2015.esercizio1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Random;

public class Broker {
    private static Server[] servers = {new Server("localhost",4000)};
    private static int tcpPort=2000;

    private static class RequestHandler extends Thread {
        private Socket reqSocket;
        private DatagramSocket inviaRichieste;

        public RequestHandler(Socket reqSocket, DatagramSocket inviaRichieste){
            this.reqSocket=reqSocket;
            this.inviaRichieste=inviaRichieste;
        }

        public void run(){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(reqSocket.getInputStream());
                Richiesta richiesta = (Richiesta) objectInputStream.readObject();
                System.out.println("Il broker ha ricevuto la richiesta: "+richiesta.toString());
                byte[] msg = richiesta.toString().getBytes();
                //scelta server casuale
                Server sendTo = servers[(int) Math.random()* servers.length];
                InetAddress address = InetAddress.getByName(sendTo.getHostname());
                DatagramPacket datagramPacket = new DatagramPacket(msg,msg.length,address,sendTo.getUdpPort());
                inviaRichieste.send(datagramPacket);
                System.out.println("IL broker ha inoltrato la richiesta ad un server.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        try {
            //ricevo la richiesta
            ServerSocket accettaRichieste = new ServerSocket(tcpPort);
            DatagramSocket inviaRichieste = new DatagramSocket();
            while(true) {
                Socket reqSocket = accettaRichieste.accept();
                new RequestHandler(reqSocket,inviaRichieste).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
