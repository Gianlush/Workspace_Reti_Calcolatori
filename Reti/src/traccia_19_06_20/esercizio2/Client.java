package traccia_19_06_20.esercizio2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {


    public void avvia(){
        try{
            Socket socket = new Socket("localhost",3000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("lotteria1,5");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Biglietto> biglietti = (ArrayList<Biglietto>) ois.readObject();

        }catch(Exception e){

        }
    }

    public static void main(String[] args){
        new Client().avvia();
    }

}
