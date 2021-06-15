package traccia_04_09_19.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class Paziente {

    private int attesaMax = 120000;
    private int medicoAssegnato;
    private int prenotazione;
    private int esame;

    public void prenota(){
        try {
            Socket socket = new Socket("localhost",3000);
            socket.setSoTimeout(attesaMax);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(""+esame);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String risposta = (String) ois.readObject();
            if(risposta.equals("service not available"))
                return;
            StringTokenizer st = new StringTokenizer(risposta,",");
            esame=Integer.parseInt(st.nextToken());
            prenotazione=Integer.parseInt(st.nextToken());
            medicoAssegnato=Integer.parseInt(st.nextToken());

            System.out.println("ricevuta la risposta: "+risposta);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cancellaPrenotazione(){
        try{
            Socket socket = new Socket("localhost",4000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(prenotazione+","+esame);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Paziente p = new Paziente();
        p.prenota();
        p.cancellaPrenotazione();
    }

}
