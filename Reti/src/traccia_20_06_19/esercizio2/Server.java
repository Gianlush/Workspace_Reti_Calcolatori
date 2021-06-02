package traccia_20_06_19.esercizio2;

import traccia_20_06_19.esercizio1.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Server {

    private static Map<Integer,HashMap<String,Dato>> listaDati = Collections.synchronizedMap(new HashMap<>());

    private static class SocketHandler1 extends Thread {

        public void run(){
            try {
                ServerSocket serverSocket = new ServerSocket(3000);
                while (true) {
                    Socket client1 = serverSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(client1.getInputStream());
                    String richiesta = (String) objectInputStream.readObject();

                    System.out.println("ricevuta richiesta: "+richiesta);

                    StringTokenizer stringTokenizer = new StringTokenizer(richiesta,"><,");
                    String idCentralina = stringTokenizer.nextToken();
                    String grandezza = stringTokenizer.nextToken().trim();

                    Dato dato = null;
                    synchronized (listaDati) {
                        dato = listaDati.get(idCentralina).get(grandezza);
                    }

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client1.getOutputStream());
                    objectOutputStream.writeObject(dato);

                    System.out.println("inviata risposta");

                }
            } catch(IOException | ClassNotFoundException e){
                    e.printStackTrace();
            }
        }
    }

    private static class SocketHandler2 extends Thread {

        public void run(){
            try{
                ServerSocket serverSocket = new ServerSocket(4000);
                while(true){
                    Socket client2 = serverSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(client2.getInputStream());
                    String richiesta = (String) objectInputStream.readObject();

                    StringTokenizer stringTokenizer = new StringTokenizer(richiesta,"<>,");
                    String idCentralina = stringTokenizer.nextToken();
                    String grandezza = stringTokenizer.nextToken();
                    long timestamp1 = Long.parseLong(stringTokenizer.nextToken());
                    long timestamp2 = Long.parseLong(stringTokenizer.nextToken());

                    int cont = 0;
                    double tot = 0;
                    for(Dato d : listaDati.get(idCentralina).values())
                        if(d.getTimestamp()<timestamp2 && d.getTimestamp()>timestamp1 && d.getGrandezza().equals(grandezza)) {
                            tot += d.getTimestamp();
                            cont++;
                        }

                    Dato dato = new Dato(grandezza,tot,-1);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client2.getOutputStream());
                    objectOutputStream.writeObject(dato);

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private static class SocketHandler3 extends Thread{

        public void run(){
            try{
                ServerSocket serverSocket = new ServerSocket(5000);
                while(true){
                    Socket centralina = serverSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(centralina.getInputStream());
                    int id = Integer.parseInt((String) objectInputStream.readObject());


                    if(!listaDati.containsKey(id)){
                        listaDati.put(id,new HashMap<>());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(centralina.getOutputStream());
                        Long l = (long) 3;
                        objectOutputStream.writeObject(l);
                        System.out.println("aggiunta centralina con id: "+id);
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private static class SocketHandler4 extends Thread {

        public void run(){
            try{
                DatagramSocket datagramSocket = new DatagramSocket(6000);
                while(true){
                    byte[] buff = new byte[128];
                    DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
                    datagramSocket.receive(datagramPacket);

                    String msg = new String(buff);
                    StringTokenizer stringTokenizer = new StringTokenizer(msg,"<>,");
                    int id = Integer.parseInt(stringTokenizer.nextToken());
                    String grandezza = stringTokenizer.nextToken();
                    double valore = Double.parseDouble(stringTokenizer.nextToken());
                    long timestamp = Long.parseLong(stringTokenizer.nextToken());

                    if(!listaDati.containsKey(id))
                        continue;

                    Dato d = new Dato(grandezza,valore,timestamp);
                    synchronized (listaDati){
                        listaDati.get(id).put(grandezza,d);
                    }

                    System.out.println("aggiunta misura: "+d.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static void avvia(){
        new SocketHandler1().start();
        new SocketHandler2().start();
        new SocketHandler3().start();
        new SocketHandler4().start();
    }

    public static void main(String[] args) {
        Server.avvia();
    }

}
