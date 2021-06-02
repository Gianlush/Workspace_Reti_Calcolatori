package traccia_20_06_19.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static class ClientTipo1 extends Thread {
        public void run(){
            try{
                Socket socket = new Socket("localhost",3000);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject("<"+1+",pressione>");

                System.out.println("inviata richiesta tipo 1");

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Dato dato = (Dato) objectInputStream.readObject();

                System.out.println("ricevuta risposta: "+dato.toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ClientTipo2 extends Thread {
        public void run(){
            try{
                Socket socket = new Socket("localhost",3000);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject("<"+1+","+(System.currentTimeMillis()-10000)+","+(System.currentTimeMillis()-10000)+">");

                System.out.println("inviata richiesta tipo 2");

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Dato dato = (Dato) objectInputStream.readObject();

                System.out.println("ricevuta risposta: "+dato.toString());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ClientTipo1().start();
        //new ClientTipo2().start();
    }

}
