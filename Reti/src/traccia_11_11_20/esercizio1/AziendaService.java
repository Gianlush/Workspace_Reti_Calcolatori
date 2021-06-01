package traccia_11_11_20.esercizio1;

import java.util.HashMap;

public class AziendaService {

    private HashMap<Integer,Azienda> aziende;

    public void vendita(int idAzienda, String nomeVino, double importo){
        Azienda a = aziende.get(idAzienda);
        a.getImporti().put(nomeVino,importo);
    }

    public IncassoProdotto maggioreIncasso(int idAzienda){
        Azienda a = aziende.get(idAzienda);
        double incassoMax=0;
        String vinoMax = "";
        for(String vino : a.getImporti().keySet())
            if(a.getImporti().get(vino)>incassoMax){
                incassoMax=a.getImporti().get(vino);
                vinoMax=vino;
            }
        return new IncassoProdotto(vinoMax,incassoMax);

    }
}
