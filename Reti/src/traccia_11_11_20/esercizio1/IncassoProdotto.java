package traccia_11_11_20.esercizio1;

public class IncassoProdotto {
    private String nome;
    private double importo;

    public IncassoProdotto(String nome, double importo) {
        this.nome = nome;
        this.importo = importo;
    }

    public String getNome() {
        return nome;
    }

    public double getImporto() {
        return importo;
    }
}
