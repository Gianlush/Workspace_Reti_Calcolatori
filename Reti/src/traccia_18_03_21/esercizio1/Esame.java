package traccia_18_03_21.esercizio1;

import java.util.HashMap;

public class Esame {
    private String codice;
    private HashMap<Studente,Integer> votiStudenti;

    public Esame(String codice) {
        this.codice = codice;
        votiStudenti = new HashMap<>();
    }

    public void aggiungiStudente(Studente s, int voto){
        votiStudenti.put(s,voto);
    }

    public String getCodice() {
        return codice;
    }

    public HashMap<Studente, Integer> getVotiStudenti() {
        return votiStudenti;
    }
}
