package traccia_10_02_2021.esercizio1;

import java.util.HashMap;

public class Impianto {
    private int id;
    private HashMap<String,Prodotto> mappaIdProdotti;
    private HashMap<String,Integer> mappaProdottoQuantita;

    public int getId() {
        return id;
    }

    public HashMap<String, Prodotto> getMappaIdProdotti() {
        return mappaIdProdotti;
    }

    public HashMap<String, Integer> getMappaProdottoQuantita() {
        return mappaProdottoQuantita;
    }
}
