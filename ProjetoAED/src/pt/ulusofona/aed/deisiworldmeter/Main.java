package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    static ArrayList<Pais> paises = new ArrayList<>();
    static ArrayList<Cidade> cidades = new ArrayList<>();
    static ArrayList<Populacao> populacao = new ArrayList<>();
    static ArrayList<InputInvalido> erros = new ArrayList<>();


    public static ArrayList getObjects(TipoEntidade tipo) {
        if (tipo == TipoEntidade.PAIS) {
            return paises;
        }
        if (tipo == TipoEntidade.CIDADE) {
            return cidades;
        }
        if(tipo == TipoEntidade.INPUT_INVALIDO){
            return erros;
        }
        return new ArrayList<>();

    }

    public static boolean parseFiles(File folder){
        paises.clear();
        cidades.clear();
        populacao.clear();
        erros.clear();

        if (folder == null || !folder.exists() || !folder.isDirectory()) {
            return false;
        }
        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            return false;
        }
        Arrays.sort(csvFiles);
        System.out.println("Ficheiros encontrados:");
        for (File f : csvFiles) {
            System.out.println(f.getName());
        }
        for (File file : csvFiles) {
            try {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                InputInvalido inputInvalido = new InputInvalido();
                inputInvalido.nomeFicheiro = file.getName();
                inputInvalido.primeiraLinhaIncorreta = -1;
                int numeroLinha = 1;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] campos = line.split(",");
                    boolean linhaValida = false;
                    numeroLinha++;


                    if (campos.length == 5) {
                        // Populacao
                        try {
                            Populacao p = new Populacao();
                            p.id = Integer.parseInt(campos[0].trim());
                            p.ano = Integer.parseInt(campos[1].trim());
                            p.populacaoMasculina = Integer.parseInt(campos[2].trim());
                            p.populacaoFeminina = Integer.parseInt(campos[3].trim());
                            p.densidade = Double.parseDouble(campos[4].trim());

                            linhaValida = true;
                            populacao.add(p);
                        } catch (NumberFormatException e) {
                            // linha inválida, ignora


                        }
                    } else if (campos.length == 4) {
                        // Pais

                        try {
                            if (campos[1].trim().isEmpty() || campos[2].trim().isEmpty() || campos[3].trim().isEmpty()) {
                                throw new NumberFormatException("campo vazio");
                            }
                            Pais p = new Pais();
                            p.id = Integer.parseInt(campos[0].trim());
                            p.alfa2 = campos[1].trim();
                            p.alfa3 = campos[2].trim();
                            p.nome = campos[3].trim();
                            if (p.id > 700) {
                                int repeticoes = 0; // começa em 0
                                for (Pais paisExistente : paises) {
                                    if (paisExistente.id == p.id) {
                                        repeticoes++;
                                    }
                                }
                                repeticoes++; // conta o atual
                                p.repeticoes = repeticoes;
                                for (Pais paisExistente : paises) {
                                    if (paisExistente.id == p.id) {
                                        paisExistente.repeticoes = repeticoes;
                                    }
                                }
                            }
                            paises.add(p);
                            linhaValida = true;
                        } catch (NumberFormatException e) {
                            // linha inválida

                        }
                    } else if (campos.length == 6) {
                        // Cidade

                        try {
                            Cidade c = new Cidade();
                            c.alfa2 = campos[0].trim().toUpperCase();
                            c.cidade = campos[1].trim();
                            c.regiao = Integer.parseInt(campos[2].trim());
                            if(!campos[3].trim().isEmpty()){
                                c.populacao = Double.parseDouble(campos[3].trim());
                            } else {
                                c.populacao = 0;
                            }
                            c.latitude = Double.parseDouble(campos[4].trim());
                            c.longitude = Double.parseDouble(campos[5].trim());

                            linhaValida = true;
                            cidades.add(c);
                        } catch (NumberFormatException e) {
                            // linha invalida

                        }
                    }
                    if (linhaValida) {
                        inputInvalido.linhasCorretas++;
                    } else {
                        inputInvalido.linhasIncorretas++;
                        if (inputInvalido.primeiraLinhaIncorreta == -1) {
                            inputInvalido.primeiraLinhaIncorreta = numeroLinha;
                        }
                    }
                }
                erros.add(inputInvalido);
                scanner.close();
            } catch (FileNotFoundException e) {
                return false;
            }
        }
        return true;
    }


        public static void main(String[] args){



            System.out.println("Bem-vindo ao DEISI World Meter");
            File folder = new File("src/pt/ulusofona/aed/deisiworldmeter");
            boolean resultado = parseFiles(folder);
            System.out.println("parseFiles: " + resultado);

            long start = System.currentTimeMillis();

            System.out.println("--- PAISES ---");
            for (Object p : getObjects(TipoEntidade.PAIS)) {
                System.out.println(p);
            }

            System.out.println("--- CIDADES ---");
            for (Object c : getObjects(TipoEntidade.CIDADE)) {
                System.out.println(c);
            }

            System.out.println("--- INPUT INVALIDO ---");
            for (Object e : getObjects(TipoEntidade.INPUT_INVALIDO)) {
                System.out.println(e);
            }
            long end = System.currentTimeMillis();
            System.out.println("Ficheiros lidos com sucesso em " + (end - start) + " ms ");
            System.out.println("Total cidades: " + cidades.size());
            System.out.println("Erros cidades: " + erros.get(0).linhasIncorretas);
        }

    }

