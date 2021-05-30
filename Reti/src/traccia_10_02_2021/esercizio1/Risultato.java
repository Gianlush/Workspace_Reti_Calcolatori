package traccia_10_02_2021.esercizio1;

public class Risultato {
    private int idImpianto;
    private int quantita;

    public Risultato(int idImpianto, int quantita) {
        this.idImpianto = idImpianto;
        this.quantita = quantita;
    }

    public int getIdImpianto() {
        return idImpianto;
    }

    public int getQuantita() {
        return quantita;
    }
}
