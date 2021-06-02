package traccia_20_06_19.esercizio1;

import java.util.HashMap;

public class Ristorante {

    private String pIva;
    private String citta;
    private HashMap<Data,Double> guadagni;
    private double guadagnoTot;



    public String getPIva() {
        return pIva;
    }

    public HashMap<Data, Double> getGuadagni() {
        return guadagni;
    }

    public String getCitta() {
        return citta;
    }

    public double getGuadagnoTot() {
        return guadagnoTot;
    }
}
