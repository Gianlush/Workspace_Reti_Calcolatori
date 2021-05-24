package gareDiAppalto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Giudice {
    private static int gPort = 3000;
    private static int rPort = 2000;
    private static int oPort = 4000;
    private static int nPartecipanti = 10;

    public static void main(String[] args){
        try {
            InetAddress group = InetAddress.getByName("230.0.0.1");

            //ricevo la richiesta
            ServerSocket serverRichieste = new ServerSocket(rPort);
            Socket riceviRichieste = serverRichieste.accept();
            ObjectInputStream ois = new ObjectInputStream(riceviRichieste.getInputStream());
            Richiesta req = (Richiesta) ois.readObject();
            System.out.println("Ricevuta: "+req);

            //invio a tutti i dettagli della richiesta
            invioMCast(req,group);

            //ricevo le offerte da tutti gli altri
            Offerta vincente = riceviOfferte();

            //invio l'esito al vincitore e a tutti gli altri
            ObjectOutputStream outputStream = new ObjectOutputStream(riceviRichieste.getOutputStream());
            outputStream.writeObject(vincente);
            invioNotificaEsito(vincente,group);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Offerta riceviOfferte(){
        Offerta vincente = null;
        try {
            ServerSocket serverOfferte = new ServerSocket(oPort);
            for(int i=0;i<nPartecipanti;i++){
                Socket riceviOfferta = serverOfferte.accept();
                ObjectInputStream ois = new ObjectInputStream(riceviOfferta.getInputStream());
                Offerta offerta = (Offerta) ois.readObject();
                if(vincente==null)
                    vincente=offerta;
                else if(vincente.getImporto()>offerta.getImporto())
                    vincente=offerta;
                else if(vincente.getImporto()==offerta.getImporto() || vincente.getId()>offerta.getId())
                    vincente=offerta;
                riceviOfferta.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vincente;
    }

    public static void invioMCast(Richiesta req, InetAddress group){
        try {
            MulticastSocket mCastSocket = new MulticastSocket();
            byte[] buf = new byte[128];
            buf = req.toString().getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, group, gPort);
            mCastSocket.send(datagramPacket);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void invioNotificaEsito(Offerta offerta, InetAddress group) throws IOException {
        MulticastSocket mCastSocket = new MulticastSocket();
        byte[] buf = new byte[64];
        buf = (offerta.getId()+" - "+ offerta.getImporto()).getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length,group,gPort);
        mCastSocket.send(datagramPacket);
    }
}
