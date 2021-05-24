package communicator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Communicator {

    private String mCastAddress = "230.0.0.1";
    private int mCastPort = 2000;
    private int tcpPort = 8080;

    public void sendMcastDatagram(){
        try {

            MulticastSocket mCastSocket = new MulticastSocket(mCastPort);
            byte[] buf = new byte[64];
            buf = ("" + tcpPort).getBytes();
            InetAddress inetAddress = InetAddress.getByName(mCastAddress);
            DatagramPacket dp = new DatagramPacket(buf,buf.length,inetAddress,mCastPort);
            System.out.println("Invio datagramma multicast.");
            mCastSocket.send(dp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
