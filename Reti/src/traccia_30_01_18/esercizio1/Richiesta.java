package traccia_30_01_18.esercizio1;

import java.io.Serializable;

public class Richiesta implements Serializable {

    private int idProdotto,quantita;

    public Richiesta(int idProdotto, int quantita) {
        this.idProdotto = idProdotto;
        this.quantita = quantita;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    @Override
    public String toString() {
        return "Richiesta{" + "idProdotto=" + idProdotto + ", quantita=" + quantita + '}';
    }
}
