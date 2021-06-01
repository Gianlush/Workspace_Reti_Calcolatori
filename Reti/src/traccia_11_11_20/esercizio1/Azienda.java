package traccia_11_11_20.esercizio1;

import java.util.HashMap;

public class Azienda {
    private int id;
    private HashMap<String,Double> importi;

    public Azienda(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public HashMap<String, Double> getImporti() {
        return importi;
    }
}
