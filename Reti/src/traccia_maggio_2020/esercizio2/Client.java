package traccia_maggio_2020.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

public class Client extends Thread implements Serializable {

    private InetAddress address;
    private int id;
    private int prodotto;
    private int cifraOfferta;

    public Client(int id, int prodotto, int cifraOfferta) {
        this.id=id;
        this.prodotto=prodotto;
        this.cifraOfferta=cifraOfferta;

        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getAddress() {
        return address;
    }

    public void run(){
        try {
            Socket socket = new Socket("localhost",3000);
            Offerta offerta = new Offerta(prodotto,this,cifraOfferta);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(offerta);

            sleep(500);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String msg = (String) objectInputStream.readObject();
            System.out.println("Il cliente "+id+" ha ricevuto la risposta: "+msg);

            if(msg.trim().equals("SCADUTO") || msg.trim().equals("TROPPO BASSA"))
                return;
            DatagramSocket datagramSocket = new DatagramSocket(4000,address);
            byte[] buff = new byte[64];
            DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
            datagramSocket.receive(datagramPacket);

            System.out.println("Il cliente "+id+" ha ricevuto la risposta: "+new String(buff).trim());



        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
