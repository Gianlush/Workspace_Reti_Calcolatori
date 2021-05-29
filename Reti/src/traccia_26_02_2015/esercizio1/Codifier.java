package traccia_26_02_2015.esercizio1;

import java.util.StringTokenizer;

public class Codifier {

    public int calcolaEta(String codifica,int anno){
        String annoNascita = codifica.substring(6,codifica.length());
        int annoNascitaInt = Integer.parseInt(annoNascita);
        return anno-annoNascitaInt;
    }

    public String codifica(Individuo persona){
        String nome = persona.getNome().substring(3);
        String cognome = persona.getCognome().substring(3,6);
        String anno = persona.getAnnoNascita()+"";
        return nome+cognome+anno;
    }
}
