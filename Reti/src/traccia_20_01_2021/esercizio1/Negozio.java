package traccia_20_01_2021.esercizio1;

import java.util.TreeMap;

public class Negozio {
    private String provincia;
    private TreeMap<Data,Double> incassi;
    private String pIva;

    public Negozio(String provincia, TreeMap<Data, Double> incassi, String pIva) {
        this.provincia = provincia;
        this.incassi = incassi;
        this.pIva = pIva;
    }

    public String getProvincia() {
        return provincia;
    }

    public TreeMap<Data, Double> getIncassi() {
        return incassi;
    }

    public String getpIva() {
        return pIva;
    }
}
