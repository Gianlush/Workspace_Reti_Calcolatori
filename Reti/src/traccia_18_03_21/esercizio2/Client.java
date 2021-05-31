package traccia_18_03_21.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

    public void run(){
        try {
            Socket socket = new Socket("localhost",1111);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("<Smartphone XZY,2,25>");
            System.out.println("Il client ha inviato una richiesta.");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String risposta = (String) objectInputStream.readObject();
            System.out.println("Il server ha risposto: "+risposta);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
