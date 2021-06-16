package traccia_19_06_20.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Client {


    public void acquista(){
        try{
            Socket socket = new Socket("lotterie.unical.it",3000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("lotteria1,5");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Biglietto> biglietti = (ArrayList<Biglietto>) ois.readObject();

        }catch(Exception e){

        }
    }

    public void riceviRisultati(){
        try{
            InetAddress group = InetAddress.getByName("230.0.0.1");
            MulticastSocket ms = new MulticastSocket(4000);
            ms.joinGroup(group);
            byte[] buff = new byte[128];
            DatagramPacket dp = new DatagramPacket(buff,buff.length);

            ms.receive(dp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Client client = new Client();
        client.acquista();
        client.riceviRisultati();
    }

}
