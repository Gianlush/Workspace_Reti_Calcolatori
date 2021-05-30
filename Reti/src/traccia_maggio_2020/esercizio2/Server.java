package traccia_maggio_2020.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Server {

    private static int tcpPort=3000;
    private static int udpPort = 4000;

    private static HashMap<Prodotto,Boolean> scadenze = new HashMap<>();
    private static HashMap<Integer,Prodotto> prodotti = new HashMap<>();
    private static HashMap<Prodotto, ArrayList<InetAddress>> clientiPerdenti = new HashMap<>();
    private static HashMap<Prodotto,InetAddress> vincitori = new HashMap<>();




    private static class RequestHandler extends Thread {
        private ServerSocket socket;
        private HashMap<Prodotto,Integer> offertaMax = new HashMap<>();

        public RequestHandler(ServerSocket socket){
            this.socket=socket;
        }

        public void run(){
            while(true){
                try {
                    Socket accettaOfferte = socket.accept();
                    ObjectInputStream objectInputStream = new ObjectInputStream(accettaOfferte.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(accettaOfferte.getOutputStream());
                    Offerta offerta = (Offerta) objectInputStream.readObject();
                    Prodotto prodotto = prodotti.get(offerta.getProdotto());

                    if(!scadenze.get(prodotto)) {
                        objectOutputStream.writeObject("SCADUTO");
                        break;
                    }
                    else if(offerta.getCifraOfferta()<prodotto.getPrezzoMinimo()) {
                        objectOutputStream.writeObject("TROPPO BASSA");
                        break;
                    }


                    if(!offertaMax.containsKey(prodotto) || offerta.getCifraOfferta()>offertaMax.get(prodotto)) {
                        offertaMax.put(prodotto,offerta.getCifraOfferta());
                        vincitori.put(prodotto,offerta.getClient().getAddress());
                    }
                    else
                        clientiPerdenti.get(prodotto).add(offerta.getClient().getAddress());

                    objectOutputStream.writeObject("OK");

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private static class DeadlineHandler extends Thread {
        private Prodotto prodotto;

        public DeadlineHandler(Prodotto p){
            prodotto=p;
        }

        public void run(){
            try {
                sleep(prodotto.getDurata());
                System.out.println("Il prodotto "+prodotto.getId()+" è scaduto.");
                scadenze.put(prodotto,false);

                DatagramSocket datagramSocket = new DatagramSocket();
                if(vincitori.containsKey(prodotto)) {
                    byte[] vinci = ("VINCITORE_" + prodotto.getId()).getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(vinci, vinci.length, vincitori.get(prodotto), udpPort);
                    datagramSocket.send(datagramPacket);
                }
                if(clientiPerdenti.containsKey(prodotto)){
                    byte[] perdi = ("NON_VINCITORE_" + prodotto.getId()).getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(perdi, perdi.length, null, udpPort);
                    for (InetAddress i : clientiPerdenti.get(prodotto)) {
                        datagramPacket.setAddress(i);
                        datagramSocket.send(datagramPacket);
                    }
                }
                else
                    System.out.println("Nessuno ha fatto offerte per il prodotto: "+prodotto.getId());

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void registraProdotto(Prodotto p){
        scadenze.put(p,true);
        prodotti.put(p.getId(),p);
        clientiPerdenti.put(p,new ArrayList<>());
        new DeadlineHandler(p).start();
    }

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(tcpPort);
            new RequestHandler(serverSocket).start();

            registraProdotto(new Prodotto(1,5000,30));
            System.out.println("Il server ha registrato il prodotto: 1");
            registraProdotto(new Prodotto(2,10000,10));
            System.out.println("Il server ha registrato il prodotto: 2");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
