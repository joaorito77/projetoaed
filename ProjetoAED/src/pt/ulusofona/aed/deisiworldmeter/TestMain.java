package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.ArrayList;

public class TestMain {

    @BeforeEach
    public void setup() {
        Main.paises.clear();
        Main.cidades.clear();
        Main.populacao.clear();
        Main.erros.clear();
    }
    @Test
    public void testParseFilesRetornaTrue() {
        File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
        boolean resultado = Main.parseFiles(folder);
        assertTrue(resultado);
    }

    @Test
    public void testPaisesToString() {
        File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
        Main.parseFiles(folder);

        ArrayList paises = Main.getObjects(TipoEntidade.PAIS);
        assertEquals(7, paises.size());

        // verifica o formato do toString do primeiro país
        Pais p = (Pais) paises.get(0);
        String result = p.toString();
        assertTrue(result.contains(p.nome));
        assertTrue(result.contains(p.alfa2.toUpperCase()));
        assertTrue(result.contains(p.alfa3.toUpperCase()));
    }

    @Test
    public void testCidadesToString() {
        File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
        Main.parseFiles(folder);

        ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);
        assertFalse(cidades.isEmpty());

        // verifica formato: cidade | alfa2 | regiao | populacao | (lat,lon)
        Cidade c = (Cidade) cidades.get(0);
        String result = c.toString();
        assertTrue(result.contains(c.cidade));
        assertTrue(result.contains(c.alfa2.toUpperCase()));
        assertTrue(result.contains("("));
        assertTrue(result.contains(")"));
        assertTrue(result.contains(","));
    }

    @Test
    public void testCidadesAlfa2Maiusculas() {
        File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
        Main.parseFiles(folder);

        ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);
        for (Object obj : cidades) {
            Cidade c = (Cidade) obj;
            assertEquals(c.alfa2.toUpperCase(), c.alfa2);
        }
    }

    @Test
    public void testInputInvalidoUmObjetoPorFicheiro() {
        File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
        Main.parseFiles(folder);

        ArrayList erros = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        // deve retornar 1 objeto por ficheiro CSV na pasta
        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        assertEquals(csvFiles.length, erros.size());

        // verifica formato do toString
        InputInvalido ii = (InputInvalido) erros.get(0);
        String result = ii.toString();
        assertTrue(result.contains("|"));
        assertTrue(result.endsWith(".csv") == false); // tem mais info depois do nome
    }
    @Test
    public void testParseFilesRetornaTrueouFalse() {
        System.out.println("Working directory: " + new File(".").getAbsolutePath());
        File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
        System.out.println("Pasta existe? " + folder.exists());
        boolean resultado = Main.parseFiles(folder);
        assertTrue(resultado);
    }

}