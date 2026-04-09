package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    String alfa2;
    String cidade;
    int regiao;
    double populacao;
    double latitude;
    double longitude;

    @Override
    public String toString() {
        return cidade + " | " + alfa2.toUpperCase() + " | " + regiao + " | " + (int)populacao + " | (" + latitude + "," + longitude + ")";
    }
}
