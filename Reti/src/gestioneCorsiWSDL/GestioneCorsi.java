package gestioneCorsiWSDL;

import java.util.HashMap;

public class GestioneCorsi {
    private HashMap<String,Corso> corsi = new HashMap<>();

    public void aggiungiCorso(Corso corso){
        corsi.put(corso.getNome(),corso);
    }

    public Corso getCorso(String nomeCorso){

        return corsi.get(nomeCorso);
    }
}
