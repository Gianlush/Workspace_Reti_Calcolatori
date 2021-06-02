package traccia_11_07_19.esercizio2;

import traccia_20_06_19.esercizio1.Data;
import traccia_20_06_19.esercizio2.Centralina;
import traccia_20_06_19.esercizio2.Dato;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Nave {

    public void avvia(){
        try{
            new CodaListener().start();
            Socket socket = new Socket("localhost",3000);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("<1,120,65>");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static class CodaListener extends Thread {

        public void run(){
            setDaemon(true);
            try{
                DatagramSocket datagramSocket = new DatagramSocket(5000);
                byte[] buff = new byte[128];
                DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
                while(true){
                    datagramSocket.receive(datagramPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
