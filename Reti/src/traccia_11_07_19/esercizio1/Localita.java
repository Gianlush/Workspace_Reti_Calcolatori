package traccia_11_07_19.esercizio1;

public class Localita {

    private Coordinate coordinate;
    private String nome;
    private String stato;

    public Localita(Coordinate coordinate, String nome, String stato) {
        this.coordinate = coordinate;
        this.nome = nome;
        this.stato = stato;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getNome() {
        return nome;
    }

    public String getStato() {
        return stato;
    }
}
