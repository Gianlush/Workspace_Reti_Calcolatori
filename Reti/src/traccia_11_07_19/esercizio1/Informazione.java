package traccia_11_07_19.esercizio1;

public class Informazione {

    private String condizione;
    private double temperaturaMin;
    private double temperaturaMax;

    public String getCondizione() {
        return condizione;
    }

    public double getTemperaturaMin() {
        return temperaturaMin;
    }

    public double getTemperaturaMax() {
        return temperaturaMax;
    }

    public Informazione(String condizione, double temperaturaMin, double temperaturaMax) {
        this.condizione = condizione;
        this.temperaturaMin = temperaturaMin;
        this.temperaturaMax = temperaturaMax;
    }
}
