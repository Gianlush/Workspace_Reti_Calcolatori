package communicator;

public class Main {

    public static void main(String[] args){
        try {
            int multicastPort = 2000;
            int socketPort = 8080;
            MulticastListener ml = new MulticastListener(multicastPort, socketPort);
            SocketListener sl = new SocketListener(socketPort);
            ml.start();
            sl.start();
            new Communicator().sendMcastDatagram();
        }catch(Exception e){
            }
    }
}
