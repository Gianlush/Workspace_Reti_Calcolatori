package traccia_11_07_19.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Operatore {

    private int tcpPort = 4000;

    public void avvia(){
        try{
            ServerSocket serverSocket = new ServerSocket(4000);
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            int idNave = Integer.parseInt((String) objectInputStream.readObject());


            TimeUnit.SECONDS.sleep(6);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("OK");

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
