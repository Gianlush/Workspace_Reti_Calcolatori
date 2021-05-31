package traccia_18_03_21.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Negozio extends Thread {
    private int id;
    private InetAddress address;
    private int tcpPort = 2222;
    private int udpPort = 3333;

    public Negozio(int id) {
        this.id = id;
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getNegozioId() {
        return id;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void run(){
        while(true){
            try{
                ServerSocket serverSocket = new ServerSocket(tcpPort);
                Socket socket = serverSocket.accept();

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                String richiesta = (String) objectInputStream.readObject();

                System.out.println("Ricevuta richiesta: "+richiesta);

                int offerta = (int) (Math.random()*50);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(offerta+"");

                serverSocket.close();
                System.out.println("Inviata offerta: "+offerta);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Negozio(1).start();
    }
}
