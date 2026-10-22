package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import tad.ListaEncadeada;
import tad.ListaIdentificadores;

//le um dicionario de nomes um por linha e grava o candidatos.txt
public class GeradorCandidatos {

    private static Random sorteio = new Random();

    // le os nomes do dicionario para uma lista
    private static ListaEncadeada<String> lerNomes(String caminho) throws IOException {
        ListaEncadeada<String> nomes = new ListaEncadeada<String>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha = leitor.readLine();
            while (linha != null) {
                linha = linha.trim();
                if (linha.length() > 0) { //ignora linha em branco
                    nomes.add(linha);
                }
                linha = leitor.readLine();
            }
        }
        return nomes;
    }

    // sorteia um estado
    private static String sortearEstado() {
        switch (sorteio.nextInt(3)) {
            case 0:
                return "MG";
            case 1:
                return "SP";
            default:
                return "RJ";
        }
    }

    // sorteia o sexo de interesse (F, M ou I)
    private static char sortearInteresse() {
        switch (sorteio.nextInt(3)) {
            case 0:
                return 'F';
            case 1:
                return 'M';
            default:
                return 'I';
        }
    }

    // sorteia de 1 a 8 areas diferentes (a lista evita repeticao)
    private static ListaIdentificadores sortearAreas() {
        int quantidade = sorteio.nextInt(8) + 1;
        ListaIdentificadores areas = new ListaIdentificadores();
        while (areas.size() < quantidade) {
            int area = sorteio.nextInt(8) + 1;
            if (!areas.contains(area)) {
                areas.add(area);
            }
        }
        return areas;
    }

    public static void main(String[] args) {
        int quantidade = 30;
        String arquivoNomes = "nomes.txt";
        String arquivoSaida = "candidatos.txt";

        if (args.length > 0) {
            try {
                quantidade = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Quantidade invalida: " + args[0]);
                return;
            }
        }
        if (args.length > 1) {
            arquivoNomes = args[1];
        }
        if (args.length > 2) {
            arquivoSaida = args[2];
        }
        if (quantidade <= 0) {
            System.out.println("A quantidade precisa ser maior que zero.");
            return;
        }

        try {
            ListaEncadeada<String> nomes = lerNomes(arquivoNomes);
            if (nomes.isEmpty()) {
                System.out.println("O arquivo de nomes esta vazio.");
                return;
            }

            try (PrintWriter escritor = new PrintWriter(arquivoSaida)) {
                escritor.println(quantidade);
                for (int i = 0; i < quantidade; i++) {
                    String nome = nomes.get(sorteio.nextInt(nomes.size()));
                    char sexo = 'F';
                    if (sorteio.nextBoolean()) {
                        sexo = 'M';
                    }
                    ListaIdentificadores areas = sortearAreas();

                    escritor.println("****");
                    escritor.println(nome);
                    escritor.println(sortearEstado());
                    escritor.println(sexo);
                    escritor.println(sortearInteresse());
                    escritor.println(areas.size());
                    for (int a = 0; a < areas.size(); a++) {
                        escritor.println(areas.get(a));
                    }
                }
            }
            System.out.println(quantidade + " candidatos gravados em " + arquivoSaida);
        } catch (IOException e) {
            System.out.println("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }
}
