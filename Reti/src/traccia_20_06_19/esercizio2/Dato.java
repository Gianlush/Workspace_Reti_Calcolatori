package traccia_20_06_19.esercizio2;

import java.io.Serializable;

public class Dato implements Serializable {

    private String grandezza;
    private double valore;
    private long timestamp;

    public Dato(String grandezza, double valore, long timestamp) {
        this.grandezza = grandezza;
        this.valore = valore;
        this.timestamp = timestamp;
    }

    public String getGrandezza() {
        return grandezza;
    }

    public double getValore() {
        return valore;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Dato{" + "grandezza='" + grandezza + '\'' + ", valore=" + valore + ", timestamp=" + timestamp + '}';
    }
}
