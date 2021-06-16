package traccia_19_06_20.esercizio2;

public class Biglietto {
    private String lotteria;
    private int numero;

    public Biglietto(String lotteria, int numero) {
        this.lotteria = lotteria;
        this.numero = numero;
    }

    public String getLotteria() {
        return lotteria;
    }

    public int getNumero() {
        return numero;
    }
}
