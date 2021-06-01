package traccia_11_11_20.esercizio2;

import java.io.Serializable;

public class Tupla implements Serializable {
    private double X,Y;

    public Tupla(double x, double y) {
        X = x;
        Y = y;
    }


    public String toString(){
        return "<"+X+","+Y+">";
    }
}
