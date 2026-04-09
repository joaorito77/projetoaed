package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    int id;
    String alfa2;
    String alfa3;
    String nome;
    int repeticoes = 0;

    @Override
    public String toString() {
        if (id > 700) {
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase() + " | " + repeticoes;
        }
        return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();
    }
}

