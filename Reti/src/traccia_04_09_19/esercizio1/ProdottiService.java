package traccia_04_09_19.esercizio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProdottiService {

    private HashMap<String,Magazzino> magazzini;


    public Prodotto prodottoPiuVenduto(String produttore){
        Prodotto prodottoMax = null;
        int contMax = 0;
        for(String id : magazzini.keySet()){
            HashMap<Prodotto, Integer> prodotti = magazzini.get(id).getQuantitaProdotti();
            for(Prodotto p : prodotti.keySet())
                if(p.getProduttore().equals(produttore) && prodotti.get(p)>contMax){
                    contMax = prodotti.get(p);
                    prodottoMax = p;
                }
        }
        return prodottoMax;
    }

    public ArrayList<Prodotto> prodottiMaxIncasso(String magazzino){
        Magazzino m = magazzini.get(magazzino);
        ArrayList<Prodotto> ret = new ArrayList<>();
        HashMap<Prodotto,Integer> prodottiCopia = m.getQuantitaProdotti();
        double primoI=0,secondoI=0,terzoI=0;
        Prodotto primoP=null,secondoP=null,terzoP=null;
        for(Prodotto p : prodottiCopia.keySet()) {
            double incasso = prodottiCopia.get(p) * p.getPrezzo();
            if(incasso > primoI){
                primoI = incasso;
                primoP = p;
            }
            else if(incasso > secondoI){
                secondoI = incasso;
                secondoP = p;
            }
            else if(incasso > terzoI){
                terzoI = incasso;
                terzoP = p;
            }
        }
        ret.add(primoP);
        ret.add(secondoP);
        ret.add(terzoP);
        return ret;
    }

}
