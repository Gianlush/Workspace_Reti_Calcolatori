package traccia_30_01_18.esercizio2;

import java.util.HashMap;

public class Impianto {
    private int id;
    private HashMap<Prodotto,Integer> prodotti;


    public int getId() {
        return id;
    }

    public HashMap<Prodotto, Integer> getProdotti() {
        return prodotti;
    }
}
