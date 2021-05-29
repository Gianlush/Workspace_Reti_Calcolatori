package traccia_20_01_2021.esercizio1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class CatenaService {

    private HashMap<String,Negozio> mappaPIva;
    private HashMap<String,Negozio[]> mappaProvince;


    public String guadagnoDaA(String provincia, Data dataFine){
        Negozio[] negozi = mappaProvince.get(provincia);
        String pIvaMax=null;
        double incassoMax=0;
        for(int i=0;i<negozi.length;i++){
            TreeMap<Data,Double> incassi = negozi[i].getIncassi();
            double tot=0;
            for(Data d : incassi.keySet())
                if(d.compareTo(dataFine)<0)
                    
                    tot+=incassi.get(d);
            if(tot>incassoMax) {
                incassoMax = tot;
                pIvaMax=negozi[i].getpIva();
            }
        }
        return pIvaMax;
    }

    public ArrayList<Data> guadagno(String pIva, double minIncasso){
        ArrayList<Data> ret = new ArrayList<>();
        TreeMap<Data,Double> incassi = mappaPIva.get(pIva).getIncassi();
        for(Data d : incassi.keySet())
            if(incassi.get(d)>=minIncasso)
                ret.add(d);
        return ret;
    }

}
