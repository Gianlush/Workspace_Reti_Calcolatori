package traccia_19_06_20.esercizio2;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Lotteria {

    private String nome;
    private ArrayList<Integer> bigliettiVenduti = new ArrayList<>();
    private boolean aperta = false;
    private int maxBiglietti = 99;

    public String getNome() {
        return nome;
    }

    public boolean isAperta() {
        return aperta;
    }

    public Lotteria(String nome){
        this.nome=nome;
        aperta=true;
        new TimeoutThread().start();
    }

    public ArrayList<Biglietto> acquista(int nBiglietti){
        ArrayList<Biglietto> ret = new ArrayList<>();
        if(maxBiglietti-bigliettiVenduti.size()<nBiglietti || !aperta){
            ret.add(new Biglietto(nome,0));
            return ret;
        }
        for(int i=0;i<nBiglietti;i++){
            int num = 0;
            do {
                num = (int) (Math.random()*maxBiglietti)+1;
            } while(bigliettiVenduti.contains(num));
            ret.add(new Biglietto(nome,num));
        }
        return ret;
    }


    private class TimeoutThread extends Thread{

        public void run() {
            try {
                TimeUnit.MINUTES.sleep(60);
                aperta = false;

            } catch (Exception e) {

            }
        }
    }

}
