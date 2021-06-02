package traccia_11_07_19.esercizio1;

import java.util.HashMap;

public class MeteoService {

    private HashMap<Localita,Informazione> meteo;

    public String maxMinTemperatura(String stato){
        double maxDifferenza = 0;
        String ret = "";
        for(Localita x : meteo.keySet())
            if(x.getStato().equals(stato)){
                double differenza = meteo.get(x).getTemperaturaMax() - meteo.get(x).getTemperaturaMin();
                if(differenza>maxDifferenza){
                    maxDifferenza=differenza;
                    ret = x.getNome();
                }
            }
        return ret;
    }

    public Informazione meteoCoordinata(Coordinate coordinate){
        for(Localita l : meteo.keySet())
            if(l.getCoordinate().equals(coordinate))
                return meteo.get(l);
        return null;
    }

}
