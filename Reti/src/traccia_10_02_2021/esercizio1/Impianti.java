package traccia_10_02_2021.esercizio1;

import java.util.HashMap;

public class Impianti {

    private HashMap<Integer,Impianto> impianti;

    public void setImpianti(HashMap<Integer, Impianto> impianti) {
        this.impianti = impianti;
    }

    public void aggiorna(int idImpianto, String idProdotto, int energia){
        HashMap<String,Integer> m = impianti.get(idImpianto).getMappaProdottoQuantita();
        Prodotto p = impianti.get(idImpianto).getMappaIdProdotti().get(idProdotto);
        int quantitaPrec = m.get(idProdotto);
        p.setQuantitaEnergia(energia);
        m.put(idProdotto,quantitaPrec++);
    }

    public Risultato maggioreEnergia(String idProdotto){
        int quantitaMax=0;
        Impianto ret = null;
        for(Impianto i : impianti.values()){
            int quantita = i.getMappaProdottoQuantita().get(idProdotto);
            if(quantita>quantitaMax){
                quantitaMax=quantita;
                ret=i;
            }
        }
        int consumo = ret.getMappaIdProdotti().get(idProdotto).getQuantitaEnergia() * quantitaMax;
        return new Risultato(ret.getId(),consumo);
    }
}
