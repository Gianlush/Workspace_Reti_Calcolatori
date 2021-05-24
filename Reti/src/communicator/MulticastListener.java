package communicator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class MulticastListener extends Thread {
    private int tcpPort;
    private int mCastPort;
    private String mCastAddress = "230.0.0.1";

    public MulticastListener(int tcpPort, int mCastPort){
        this.mCastPort=mCastPort;
        this.tcpPort=tcpPort;

        System.out.println("mCastPort: "+mCastPort+". tcpPort: "+tcpPort);
    }

    public void run(){
        try {
            MulticastSocket m = new MulticastSocket(mCastPort);
            InetAddress addr = InetAddress.getByName(mCastAddress);
            m.joinGroup(addr);

            while(true) {
                byte[] buf = new byte[64];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                m.receive(dp);

                String received = new String(dp.getData());
                System.out.println("messaggio ricevuto: " + received);

                InetAddress remAddress = dp.getAddress();
                int remPort = Integer.parseInt(received);

                if (remPort != tcpPort || !remAddress.equals(InetAddress.getLocalHost())) {
                    Socket tcpSocket = new Socket(remAddress.getHostAddress(),tcpPort);
                    new PrintWriter(tcpSocket.getOutputStream(),true).println(tcpPort);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
