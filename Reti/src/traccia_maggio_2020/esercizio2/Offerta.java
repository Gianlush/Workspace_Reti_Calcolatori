package traccia_maggio_2020.esercizio2;

import java.io.Serializable;

public class Offerta implements Serializable {
    private int prodotto;
    private Client client;
    private int cifraOfferta;

    public Offerta(int prodotto, Client client, int cifraOfferta) {
        this.prodotto = prodotto;
        this.client = client;
        this.cifraOfferta = cifraOfferta;
    }

    public int getProdotto() {
        return prodotto;
    }

    public Client getClient() {
        return client;
    }

    public int getCifraOfferta() {
        return cifraOfferta;
    }
}
