package traccia_20_01_2021_incompleta.esercizio1;


import java.util.Date;

public class Data implements Comparable<Data>{
    private int giorno,mese,anno;

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public int getGiorno() {
        return giorno;
    }

    public int getMese() {
        return mese;
    }

    public int getAnno() {
        return anno;
    }

    @Override
    public int compareTo(Data o) {
        Date questaData = new Date(anno,mese,giorno);
        Date dataRicevuta = new Date(o.anno,o.mese,o.giorno);
        return questaData.compareTo(dataRicevuta);
    }
}
