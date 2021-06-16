package traccia_19_06_20.esercizio1;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributoriBenzinaService {

    private HashMap<String,ArrayList<Distributore>> distributoriPerRegione;

    public Distributore minPrezzoDiesel(String regione){
        ArrayList<Distributore> distributori = distributoriPerRegione.get(regione);
        Distributore min = null;
        double prezzoMin = distributori.get(0).getPrezzoDiesel();
        for(Distributore d : distributori){
            if(d.getPrezzoDiesel()<prezzoMin){
                prezzoMin = d.getPrezzoDiesel();
                min = d;
            }
        }
        return min;
    }

    public String regioneMinMediaBenzina(){
        String regioneMinMedia = "";
        double minMedia = 0;
        for(String regione : distributoriPerRegione.keySet()){
            ArrayList<Distributore> distributori = distributoriPerRegione.get(regione);
            double somma = 0;
            for(Distributore d : distributori)
                somma+=d.getPrezzoBenzina();
            double media = somma/distributori.size();
            if(minMedia==0 || media < minMedia){
                minMedia = media;
                regioneMinMedia = regione;
            }
        }
        return regioneMinMedia;
    }

}
