package communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener extends Thread {
    private int tcpPort;

    public SocketListener(int port) {
        this.tcpPort = port;
    }

    public void run() {
        try {
            ServerSocket sListener = new ServerSocket(tcpPort);
            while (true) {
                Socket sock = sListener.accept();
                InetAddress remAddress = sock.getInetAddress();
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String line = in.readLine();
                int remTCPPort = Integer.parseInt(line);
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
