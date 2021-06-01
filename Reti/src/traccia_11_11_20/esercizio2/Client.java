package traccia_11_11_20.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Client extends Thread {

    private int tcpPort = 1111;
    private int udpPort = 4000;
    private String mCastGroup = "230.0.0.1";

    public void run(){
        try{
            InetAddress group = InetAddress.getByName(mCastGroup);
            MulticastSocket multicastSocket = new MulticastSocket();
            multicastSocket.joinGroup(group);


            Socket socket = new Socket("localhost",tcpPort);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("<analisi1#5#8>");
            System.out.println("Il client ha inviato la richiesta.");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Tupla risposta = (Tupla) objectInputStream.readObject();
            System.out.println("Il server ha risposto con: "+risposta);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().start();
    }

}
