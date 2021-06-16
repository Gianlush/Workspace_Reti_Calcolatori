package traccia_30_01_18.esercizio2;

import java.util.HashMap;

public class ImpiantiService {

    private HashMap<Integer,Impianto> impianti;


    public void aggiorna(int idImpianto, String idProdotto, int quantita){
        HashMap<Prodotto,Integer> prodotti = impianti.get(idImpianto).getProdotti();
        prodotti.replace(new Prodotto(idProdotto),quantita);
    }

    public Risultato maggioreQuantita(String idProdotto){
        int nMax = 0;
        int idImpiantoMax = 0;
        for(Impianto i : impianti.values()){
            HashMap<Prodotto,Integer> prodotti = i.getProdotti();
            if(prodotti.get(idProdotto)>nMax){
                nMax=prodotti.get(idProdotto);
                idImpiantoMax=i.getId();
            }
        }
        return new Risultato(idImpiantoMax,idProdotto,nMax);
    }
}
