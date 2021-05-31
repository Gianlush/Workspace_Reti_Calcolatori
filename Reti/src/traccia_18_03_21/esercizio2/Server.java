package traccia_18_03_21.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

public class Server {

    private ArrayList<Negozio> negozi = new ArrayList<>();
    private Map<Negozio,Integer> importoTotale = Collections.synchronizedMap(new HashMap<>());
    private int tcpRequest = 1111;
    private int tcpSend = 2222;
    private int udpSend = 3333;

    public Server(){
        try {
            InetAddress addr = InetAddress.getByName("localhost");
            negozi.add(new Negozio(1));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private class RequestHandler extends Thread {
        private ServerSocket serverSocket;

        public RequestHandler(ServerSocket socket){
            serverSocket=socket;
        }

        public void run(){
            while(true){
                try {
                    //accetta offerte dal cliente
                    Socket client = serverSocket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                    String richiesta = (String) objectInputStream.readObject();
                    int prezzoMax = estraiInfo(richiesta,3);
                    int quantita = estraiInfo(richiesta,2);

                    System.out.println("Ricevuta la richiesta: "+richiesta);

                    //inoltra richieste ai negozi
                    Socket[] inoltraRichiesta = new Socket[negozi.size()];
                    ObjectOutputStream objectOutputStream = null;
                    for(int i = 0; i<negozi.size(); i++){
                        inoltraRichiesta[i] = new Socket(negozi.get(i).getAddress(),tcpSend);
                        objectOutputStream = new ObjectOutputStream(inoltraRichiesta[i].getOutputStream());
                        objectOutputStream.writeObject(richiesta);
                    }
                    System.out.println("Inoltrate le richieste ai negozi.");

                   //riceve offerte dai negozi
                    Integer offertaMin = prezzoMax;
                    Negozio negozioVincente = null;
                    for(int i = 0; i<negozi.size(); i++){
                        objectInputStream = new ObjectInputStream(inoltraRichiesta[i].getInputStream());
                        String offertaString = (String) objectInputStream.readObject();
                        int offerta = Integer.parseInt(offertaString.trim());
                        if(offerta<offertaMin){
                            offertaMin=offerta;
                            negozioVincente=negozi.get(i);
                        }
                    }

                    System.out.println("Ricevute risposte dei negozi.");

                    //risponde al cliente con l'offerta
                    objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                    if(negozioVincente==null)
                        objectOutputStream.writeObject("Nessuna offerta è arrivata.");
                    else {
                        objectOutputStream.writeObject("< NegozioVincente: " + negozioVincente.getNegozioId() + ", offerta: " + offertaMin + ">");
                        importoTotale.put(negozioVincente,offertaMin*quantita);
                    }

                    System.out.println("Inviata risposta al client.");


                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

        public int estraiInfo(String richiesta, int n){
            StringTokenizer stringTokenizer = new StringTokenizer(richiesta, "<>,");
            String prezzo = null;
            for(int i=0;i<n;i++)
                prezzo = stringTokenizer.nextToken();
            return Integer.parseInt(prezzo.trim());
        }
    }


    private class NotifyNegozi extends Thread {
        private DatagramSocket datagramSocket;

        public NotifyNegozi(DatagramSocket datagramSocket){
            this.datagramSocket=datagramSocket;
        }

        public void run(){
            while(true){
                try {
                    sleep(10000);
                    if (!importoTotale.isEmpty()) {
                        byte[] buff = new byte[32];
                        DatagramPacket datagramPacket = null;
                        for (Negozio n : importoTotale.keySet()) {
                            buff = (importoTotale.get(n) + "").getBytes();
                            datagramPacket = new DatagramPacket(buff, buff.length);
                            datagramPacket.setPort(udpSend);
                            datagramPacket.setAddress(n.getAddress());
                            datagramSocket.send(datagramPacket);
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Inviata notifica giornaliera ai negozi.");
            }
        }
    }
    public void avvia(){
        try {
            ServerSocket serverSocket = new ServerSocket(tcpRequest);
            new RequestHandler(serverSocket).start();

            DatagramSocket datagramSocket = new DatagramSocket();
            new NotifyNegozi(datagramSocket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().avvia();
    }
}
