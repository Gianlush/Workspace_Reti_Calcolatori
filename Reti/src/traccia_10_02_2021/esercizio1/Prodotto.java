package traccia_10_02_2021.esercizio1;

public class Prodotto {
    private String id;
    private int quantitaEnergia;

    public Prodotto(String id, int quantitaEnergia) {
        this.id = id;
        this.quantitaEnergia = quantitaEnergia;
    }

    public String getId() {
        return id;
    }

    public int getQuantitaEnergia() {
        return quantitaEnergia;
    }

    public void setQuantitaEnergia(int quantitaEnergia) {
        this.quantitaEnergia = quantitaEnergia;
    }
}
