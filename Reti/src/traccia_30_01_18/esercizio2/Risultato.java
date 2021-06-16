package traccia_30_01_18.esercizio2;

import java.io.Serializable;

public class Risultato implements Serializable {

    private int idImpianto;
    private String idProdotto;
    private int quantita;

    public int getIdImpianto() {
        return idImpianto;
    }

    public int getQuantita() {
        return quantita;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public Risultato(int idImpianto, String idProdotto, int quantita) {
        this.idImpianto = idImpianto;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
    }
}
