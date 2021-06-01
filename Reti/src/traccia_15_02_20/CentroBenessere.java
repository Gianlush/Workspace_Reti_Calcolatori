package traccia_15_02_20;

import traccia_20_01_2021_incompleta.esercizio1.Data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class CentroBenessere extends Thread {
    private String hostname = "localhost";
    private static int tcpPort = 3333;


    public void run(){
        try{
            MulticastSocket multicastSocket = new MulticastSocket(2222);
            InetAddress mCastGroup = InetAddress.getByName("224.3.2.1");
            multicastSocket.joinGroup(mCastGroup);
            while(true){
                byte[] buff = new byte[64];
                DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
                multicastSocket.receive(datagramPacket);
                System.out.println("Riecvuta richiesta.");

                if(Math.random()>0.7) {
                    int offerta = elabora(buff);
                    Socket socket = new Socket("localhost",tcpPort);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject("<"+hostname+","+offerta+">");
                    System.out.println("offerta inviata.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int elabora(byte[] buff){
        String richiesta = new String(buff);
        StringTokenizer stringTokenizer = new StringTokenizer(richiesta,"<>,");
        String data = stringTokenizer.nextToken();
        String numPersoneString = stringTokenizer.nextToken();
        int numPersone = Integer.parseInt(numPersoneString.trim());
        int random = (int) (Math.random()*100) + 50;
        return random*numPersone;
    }

    public static void main(String[] args) {
        new CentroBenessere().start();
    }

}
