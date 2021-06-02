package traccia_11_07_19.esercizio1;

import java.util.Objects;

public class Coordinate {

    private double latitudine;
    private double longitudine;

    public Coordinate(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.latitudine, latitudine) == 0 && Double.compare(that.longitudine, longitudine) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitudine, longitudine);
    }
}
