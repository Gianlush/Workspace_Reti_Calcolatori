package traccia_04_09_19.esercizio2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Server {

    private static HashMap<Integer,Esame> esami;
    private static int attesaMax = 120000;
    private static boolean aperte;

    public static void avvia(){
        try {
            DatagramSocket ds = new DatagramSocket();
            ServerSocket ss1 = new ServerSocket(3000);
            ServerSocket ss2 = new ServerSocket(4000);

            new TimeHandler(ds).start();

            while(true){
                new PrenotazioniHandler(ss1.accept()).start();
                new CancellazioneHandler(ss2.accept()).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class PrenotazioniHandler extends Thread {
        private Socket paziente;

        public PrenotazioniHandler(Socket socket) throws SocketException {
            paziente=socket;
            socket.setSoTimeout(attesaMax);
        }

        public void run(){
            try{
                ObjectInputStream ois = new ObjectInputStream(paziente.getInputStream());
                String richiesta = (String) ois.readObject();
                int esameId = Integer.parseInt(richiesta);
                Esame esame = esami.get(esameId);
                ObjectOutputStream oos = new ObjectOutputStream(paziente.getOutputStream());

                if(!aperte) {
                    oos.writeObject("service not available");
                    return;
                }

                esame.addInCoda();
                while(esami.get(esameId).getnPrenotati()==20){
                    //attesa finche scade il socket Timeout
                }
                esame.removeInCoda();

                int medico = assegnaMedico(esameId);


                oos.writeObject(esami.get(esameId).getnPrenotati()+","+esameId+","+medico);


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private int assegnaMedico(int esame){
            Medico[] medici = esami.get(esame).getMedici();
            for(Medico m : medici)
                if(m.getPazientiAssegnati()<10) {
                    esami.get(esame).addPrenotato();
                    m.addPaziente();
                    return m.getMatricola();
                }
            return -1;
        }
    }

    private static class CancellazioneHandler extends Thread {
        private Socket paziente;

        public CancellazioneHandler(Socket socket){
            paziente=socket;
        }

        public void run(){
            try{
                ObjectInputStream  ois = new ObjectInputStream(paziente.getInputStream());
                String richiesta = (String) ois.readObject();
                StringTokenizer st = new StringTokenizer(richiesta,",");
                int prenotazione = Integer.parseInt(st.nextToken());
                int esame = Integer.parseInt(st.nextToken());
                Medico[] medici = esami.get(esame).getMedici();

                esami.get(esame).removePrenotato();
                if(prenotazione<6)
                    medici[0].removePaziente();
                else
                    medici[1].removePaziente();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private static class TimeHandler extends Thread {
        private DatagramSocket ds;

        public TimeHandler(DatagramSocket ds){
            this.ds=ds;
        }

        public void run(){
            while(true){
                try{
                    TimeUnit.HOURS.sleep(4);
                    if(aperte)
                        aperte=false;
                    else
                        aperte=true;
                    TimeUnit.HOURS.sleep(8);

                    InetAddress address = InetAddress.getByName("localhost");
                    DatagramPacket dp = null;
                    byte[] buff = null;
                    for(Esame e : esami.values()){
                        buff=new Statistica(e).toString().getBytes();
                        dp = new DatagramPacket(buff,buff.length,address,5000);
                        ds.send(dp);
                    }


                } catch (InterruptedException | UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
