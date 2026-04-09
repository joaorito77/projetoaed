package pt.ulusofona.aed.deisiworldmeter;

public class InputInvalido {
    String nomeFicheiro;
    int linhasCorretas;
    int linhasIncorretas;
    int primeiraLinhaIncorreta;

    @Override
    public String toString() {
        return nomeFicheiro + " | " + linhasCorretas + " | " + linhasIncorretas + " | " + primeiraLinhaIncorreta;
    }
}
