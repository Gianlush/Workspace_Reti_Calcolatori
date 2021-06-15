package traccia_04_09_19.esercizio2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Direzione {

    public static void avvia() {
        try {
            DatagramSocket ds = new DatagramSocket(5000);
            byte[] buff = new byte[128];
            DatagramPacket dp = new DatagramPacket(buff,buff.length);
            ds.receive(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
