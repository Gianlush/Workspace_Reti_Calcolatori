package traccia_04_09_19.esercizio2;

public class Medico {

    private int matricola;
    private int pazientiAssegnati;

    public int getMatricola() {
        return matricola;
    }

    public int getPazientiAssegnati() {
        return pazientiAssegnati;
    }

    public synchronized void addPaziente(){
        pazientiAssegnati++;
    }

    public synchronized void removePaziente(){
        pazientiAssegnati--;
    }
}
