package traccia_20_06_19.esercizio1;

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
        Date d1 = new Date(anno,mese,giorno);
        Date d2 = new Date(o.anno,o.mese,o.giorno);

        return d1.compareTo(d2);

    }
}
