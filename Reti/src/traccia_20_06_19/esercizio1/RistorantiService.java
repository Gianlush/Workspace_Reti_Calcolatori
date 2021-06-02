package traccia_20_06_19.esercizio1;

import java.util.HashMap;

public class RistorantiService {

    private HashMap<String,Ristorante> ristoranti;

    public String guadagnoDaA(String pIva,Data dataInizio,Data dataFine){
        Ristorante r = ristoranti.get(pIva);
        double cont = 0;
        for(Data d : r.getGuadagni().keySet())
            if(d.compareTo(dataFine)<0 && d.compareTo(dataInizio)>0)
                cont+=r.getGuadagni().get(d);

        return ""+cont;
    }

    public String maxGuadagno(String nomeCitta){
        double guadagnoMax = 0;
        String pIvaMax = "";
        for(Ristorante r : ristoranti.values())
            if(r.getCitta().equals(nomeCitta) && r.getGuadagnoTot()>guadagnoMax){
                guadagnoMax=r.getGuadagnoTot();
                pIvaMax=r.getPIva();
            }
        return pIvaMax;
    }

}
