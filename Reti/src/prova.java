import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class prova {

    public static void main(String...args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        Socket socket =  new Socket("localhost",8080);

        System.out.println(server.accept().getInetAddress());
        System.out.println(socket.getLocalSocketAddress());
    }
}
