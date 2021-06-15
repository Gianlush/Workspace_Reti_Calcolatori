package traccia_04_09_19.esercizio1;

import java.util.ArrayList;
import java.util.HashMap;

public class Magazzino {

    private int id;
    private HashMap<Prodotto, Integer> quantitaProdotti;

    public int getId() {
        return id;
    }

    public HashMap<Prodotto, Integer> getQuantitaProdotti() {
        return new HashMap<>(quantitaProdotti);
    }
}
