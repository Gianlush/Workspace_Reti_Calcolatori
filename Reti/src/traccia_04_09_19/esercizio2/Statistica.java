package traccia_04_09_19.esercizio2;

public class Statistica {

    private int esame;
    private int numPazientiPrenotati;
    private int numPazientiInCoda;

    public Statistica(Esame e){
        esame=e.getCodice();
        numPazientiInCoda=e.getnInCoda();
        numPazientiPrenotati=e.getnPrenotati();
    }

    @Override
    public String toString() {
        return "Statistica{" + "esame=" + esame + ", numPazientiPrenotati=" + numPazientiPrenotati + ", numPazientiInCoda=" + numPazientiInCoda + '}';
    }
}
