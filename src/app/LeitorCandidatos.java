package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tad.Candidato;
import tad.ListaEncadeada;
import tad.ListaIdentificadores;

public class LeitorCandidatos {

    // le o arquivo e devolve a lista de candidatos (o id é a posição na lista, de 0 a n-1)
    // devolve null se der algum erro na leitura
    public static ListaEncadeada<Candidato> carregarCandidatos(String caminhoArquivo) {
        ListaEncadeada<Candidato> candidatos = new ListaEncadeada<Candidato>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {

            //lê a primeira linha para pegar o N(total de candidatos)
            String linhaN = leitor.readLine();
            if (linhaN == null)
                return null;
            int n = Integer.parseInt(linhaN.trim());

            //lê candidato por candidato(0 até n-1)
            for (int i = 0; i < n; i++) {
                //lê a linha do ****
                String linha = leitor.readLine();

                //se a linha lida for separador ****, lemos a proxima linha para pegar o nome
                if (linha != null && linha.trim().equals("****")) {
                    linha = leitor.readLine();
                }

                if (linha == null) {
                    System.out.println("O arquivo terminou antes de ler todos os candidatos.");
                    return null;
                }

                String nome = linha.trim();
                String estado = leitor.readLine().trim();
                char sexo = leitor.readLine().trim().charAt(0);
                char sexoInteresse = leitor.readLine().trim().charAt(0);

                //lê a qtd de areas de interesse
                int qtdAreas = Integer.parseInt(leitor.readLine().trim());
                ListaIdentificadores areas = new ListaIdentificadores();

                //lê cada codigo da area de interesse(uma por linha)
                for (int a = 0; a < qtdAreas; a++) {
                    areas.add(Integer.parseInt(leitor.readLine().trim()));
                }

                //cria o candidato com o id correspondente a sua posição i na lista
                candidatos.add(new Candidato(i, nome, estado, sexo, sexoInteresse, areas));
            }


        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Erro de formatação numérica no arquivo: " + e.getMessage());
            return null;
        } catch (NullPointerException e) {
            System.out.println("O arquivo está incompleto ou fora do formato esperado.");
            return null;
        }
        return candidatos;
    }
}
