package traccia_26_02_2015.esercizio2;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private String hostname;
    private int port;
    private int A,B;

    public Richiesta(String hostname, int port, int a, int b) {
        this.hostname = hostname;
        this.port=port;
        A = a;
        B = b;
    }

    public String getHostname() {
        return hostname;
    }

    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public Richiesta(int port) {
        this.port = port;
    }

    public String toString(){
        return hostname+"-"+port+"-"+A+"-"+B;
    }
}
