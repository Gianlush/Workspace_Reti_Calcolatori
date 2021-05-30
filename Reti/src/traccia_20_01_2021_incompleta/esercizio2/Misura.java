package traccia_20_01_2021_incompleta.esercizio2;

import java.io.Serializable;

public class Misura implements Serializable {
    private int id;
    private double valore;
    private long timestamp;

    public Misura(int id, double valore, long timestamp) {
        this.id = id;
        this.valore = valore;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public double getValore() {
        return valore;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
