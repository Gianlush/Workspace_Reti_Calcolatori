package traccia_20_01_2021_incompleta.esercizio2;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Server {
    private static int tcpPort = 3000;
    private static int udpPort = 4000;
    private static int mCastPort = 5000;
    private static String mCastGroup = "230.0.0.1";

    private static HashMap<Integer,Misura> dati;

    private static class RequestThreadHandler extends Thread {
        private ServerSocket serverSocket;

        public RequestThreadHandler(ServerSocket socket){
            this.serverSocket=socket;
        }

        public void run() {
            while (true) {
                try {
                    Socket client = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    int server = Integer.parseInt(bufferedReader.readLine());
                    Misura m = dati.get(server);

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                    objectOutputStream.writeObject(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ServerCheckStatusThread extends Thread {
        private MulticastSocket multicastSocket;
        private InetAddress group;

        public ServerCheckStatusThread(MulticastSocket multicastSocket, InetAddress group){
            this.multicastSocket=multicastSocket;
            this.group=group;
        }

        public void run() {
            while (true) {
                try {
                    TimeUnit.MINUTES.sleep(10);
                    StringBuilder nonFuzionanti = new StringBuilder();
                    for (int i : dati.keySet()) {
                        long timestamp = dati.get(i).getTimestamp();
                        if (System.currentTimeMillis() - timestamp > TimeUnit.MINUTES.toMillis(10))
                            nonFuzionanti.append(i + "-");
                    }
                    if (nonFuzionanti.length() != 0)
                        inviaMCast(nonFuzionanti.toString());
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        }

        public void inviaMCast(String nonFunzionanti) throws IOException {
            byte[] buff = nonFunzionanti.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length,group,mCastPort);
            multicastSocket.send(datagramPacket);
        }

    }



    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(tcpPort);
            new RequestThreadHandler(serverSocket).start();

            InetAddress group = InetAddress.getByName(mCastGroup);
            MulticastSocket multicastSocket = new MulticastSocket();
            new ServerCheckStatusThread(multicastSocket,group).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
