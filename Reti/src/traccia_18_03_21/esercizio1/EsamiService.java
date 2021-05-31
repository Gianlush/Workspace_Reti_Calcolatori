package traccia_18_03_21.esercizio1;

import java.util.ArrayList;
import java.util.HashMap;

public class EsamiService {

    private HashMap<String,Esame> esami;

    public ArrayList<String> esamiStudente(String matricola){
        Studente objStudente = new Studente(matricola);
        ArrayList<String> ret = new ArrayList<>();

        for(Esame e : esami.values())
            if(e.getVotiStudenti().containsKey(objStudente))
                ret.add(e.getCodice());
        return ret;
    }

    public ArrayList<Studente> studentiPerEsame(String codEsame, int voto){
        ArrayList<Studente> ret = new ArrayList<>();
        HashMap<Studente,Integer> voti = esami.get(codEsame).getVotiStudenti();
        for(Studente s : voti.keySet())
            if(voti.get(s)>=voto)
                ret.add(s);
        return ret;
    }

}
