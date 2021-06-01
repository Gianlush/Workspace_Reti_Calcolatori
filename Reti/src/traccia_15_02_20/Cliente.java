package traccia_15_02_20;

import traccia_11_11_20.esercizio2.Client;
import traccia_11_11_20.esercizio2.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente extends Thread {

    private static int tcpPort = 1111;

    public void run(){
        try{
            Socket socket = new Socket("localhost",tcpPort);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("<oggi>,<3>");
            System.out.println("Richiesta inviata");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String offerte = (String) objectInputStream.readObject();
            System.out.println("Offerte ricevute: "+offerte);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().start();
    }

}
