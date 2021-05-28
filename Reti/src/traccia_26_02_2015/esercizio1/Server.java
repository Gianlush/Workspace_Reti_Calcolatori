package traccia_26_02_2015.esercizio1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

public class Server {
    private static String hostname;
    private static int udpPort=4000;

    public Server(String hostname, int UDPport){
        this.hostname=hostname;
        this.udpPort =UDPport;
    }

    public String getHostname() {
        return hostname;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public static void main(String[] args){
        try {
            //ricevi richiesta
            byte[] buff = new byte[64];
            DatagramSocket serverSocket = new DatagramSocket(udpPort);
            DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
            while(true) {
                serverSocket.receive(datagramPacket);
                System.out.println("Il server con porta " + udpPort + " ha ricevuto una richiesta.");

                //costruisci risposta
                String richiesta = new String(buff).trim();
                StringTokenizer stringTokenizer = new StringTokenizer(richiesta, "-");
                String clientAddress = stringTokenizer.nextToken();
                int clientPort = Integer.parseInt(stringTokenizer.nextToken());
                int A = Integer.parseInt(stringTokenizer.nextToken());
                int B = Integer.parseInt(stringTokenizer.nextToken());
                Risposta risposta = new Risposta(A + B);

                //invio risposta
                Socket invioRisposta = new Socket(clientAddress, clientPort);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(invioRisposta.getOutputStream());
                objectOutputStream.writeObject(risposta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
