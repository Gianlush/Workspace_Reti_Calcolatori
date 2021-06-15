package traccia_04_09_19.esercizio2;

public class Esame {

    private int codice;
    private Medico[] medici;
    private int nPrenotati;
    private int nInCoda;

    public Medico[] getMedici() {
        return medici.clone();
    }

    public int getCodice() {
        return codice;
    }

    public synchronized void addPrenotato(){
        nPrenotati++;
    }
    public synchronized void removePrenotato(){
        nPrenotati--;
    }


    public int getnPrenotati() {
        return nPrenotati;
    }

    public synchronized void addInCoda(){
        nInCoda++;
    }

    public synchronized void removeInCoda(){
        nInCoda--;
    }

    public int getnInCoda() {
        return nInCoda;
    }
}
