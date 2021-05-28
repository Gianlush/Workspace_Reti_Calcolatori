package gareDiAppalto;

import java.io.Serializable;

public class Offerta implements Serializable {
    private int id;
    private double importo;

    public Offerta(int id, double importo) {
        this.id = id;
        this.importo = importo;
    }

    public int getId() {
        return id;
    }

    public double getImporto() {
        return importo;
    }
}
