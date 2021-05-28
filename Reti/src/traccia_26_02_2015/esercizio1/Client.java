package traccia_26_02_2015.esercizio1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static int brokerPort = 2000;
    private static String brokerAddress = "localhost"; //broker.unical.it
    private static int localPort = 5000;
    private static int A=15,B=5;
    private static String localAddress = "localhost";

    public static void main(String[] args){
        try{
            //invio richiesta al broker
            Socket inviaRichiesta = new Socket(brokerAddress,brokerPort);
            Richiesta richiesta = new Richiesta(localAddress,localPort,A,B);
            ObjectOutputStream outputStream = new ObjectOutputStream(inviaRichiesta.getOutputStream());
            outputStream.writeObject(richiesta);
            System.out.println("Il client ha inviato la richiesta: "+A+"+"+B+"=?");

            //ricevo risposta dal server
            ServerSocket serverSocket = new ServerSocket(localPort);
            Socket serverResponse = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(serverResponse.getInputStream());
            Risposta risposta = (Risposta) objectInputStream.readObject();
            System.out.println("Il client ha ricevuto come risposta: "+risposta.getNum());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
