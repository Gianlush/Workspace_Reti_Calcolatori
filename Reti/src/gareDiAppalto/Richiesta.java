package gareDiAppalto;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private String descrizione;
    private double importoMassimo;

    public Richiesta(String descrizione, double importoMassimo){
        this.descrizione=descrizione;
        this.importoMassimo=importoMassimo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getImportoMassimo() {
        return importoMassimo;
    }

    @Override
    public String toString() {
        return descrizione +" - "+ importoMassimo;
    }
}
