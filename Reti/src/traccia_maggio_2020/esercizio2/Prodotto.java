package traccia_maggio_2020.esercizio2;

import java.net.Socket;

public class Prodotto {
    private int id;
    private long durata;
    private int prezzoMinimo;

    public Prodotto(int id) {
        this.id = id;
    }

    public Prodotto(int id, long durata, int prezzoMinimo) {
        this.id = id;
        this.durata = durata;
        this.prezzoMinimo = prezzoMinimo;
    }

    public int getId() {
        return id;
    }

    public long getDurata() {
        return durata;
    }

    public int getPrezzoMinimo() {
        return prezzoMinimo;
    }
}
