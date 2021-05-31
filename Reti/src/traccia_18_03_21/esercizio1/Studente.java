package traccia_18_03_21.esercizio1;

public class Studente {
    private String matricola,nome,cognome;

    public Studente(String matricola) {
        this.matricola = matricola;
    }

    public Studente(String matricola, String nome, String cognome) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
}
