package traccia_30_01_18.esercizio1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    private int idCliente;

    public void avvia(){
        try{
            Socket socket = new Socket("intermediario.eu",2345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new Richiesta(1,5));

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String risposta = (String) ois.readObject();

        } catch(Exception e){
        }
    }

    public static void main(String[] args) {
        new Cliente().avvia();
    }

}
