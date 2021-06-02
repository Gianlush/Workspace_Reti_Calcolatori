package traccia_20_06_19.esercizio2;

import traccia_20_06_19.esercizio1.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Centralina {

    private int idCentralina = 1;
    private long timeToWait;
    private String[] grandezze = {"pressione"};

    public int getIdCentralina() {
        return idCentralina;
    }

    public void avvia(){
        try{
            Socket socket = new Socket("localhost",5000);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(""+idCentralina);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Long time = (Long) objectInputStream.readObject();
            timeToWait = time*1000;

            new InvioHandler().start();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class InvioHandler extends Thread {

        public void run(){
            try{
                DatagramSocket datagramSocket = new DatagramSocket();
                while(true){
                    sleep(timeToWait);
                    double valore = Math.random()*50;
                    int indice = (int) (Math.random()*grandezze.length);
                    byte[] buff = ("<"+idCentralina+">,<"+grandezze[indice]+","+valore+","+System.currentTimeMillis()+">").getBytes();
                    InetAddress address = InetAddress.getByName("localhost");
                    DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length,address,6000);
                    datagramSocket.send(datagramPacket);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new Centralina().avvia();
    }

}
