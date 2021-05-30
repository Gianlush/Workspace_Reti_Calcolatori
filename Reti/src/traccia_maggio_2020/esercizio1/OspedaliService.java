package traccia_maggio_2020.esercizio1;

public class OspedaliService {

    private Ospedale[] ospedali;

    public Ospedale maxOspedale(String in0){
        int max=0;
        Ospedale ret = null;
        for(int i=0;i<ospedali.length;i++) {
            int postiLiberi = ospedali[i].getPostiLetto() - ospedali[i].getPazienti();
            if (ospedali[i].getCitta().equals(in0) && postiLiberi > max) {
                max = postiLiberi;
                ret = ospedali[i];
            }
        }
        return ret;
    }

    public String cittaRatio(){
        int maxRapporto=0;
        String ret="";
        for(Ospedale o : ospedali){
            if(o.getPazienti()/o.getPostiLetto()>maxRapporto){
                maxRapporto=o.getPazienti()/o.getPostiLetto();
                ret=o.getCitta();
            }
        }
        return ret;
    }
}
