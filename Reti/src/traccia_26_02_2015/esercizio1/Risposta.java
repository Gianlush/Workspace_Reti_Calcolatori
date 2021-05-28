package traccia_26_02_2015.esercizio1;

import java.io.Serializable;

public class Risposta implements Serializable {
    private int num;

    public Risposta(int num){
        this.num=num;
    }

    public int getNum() {
        return num;
    }
}
