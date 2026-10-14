package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tad.Candidato;

public class LeitorCandidatos {
    
    public static Candidato[] carregarCandidatos(String caminhoArquivo) {
        Candidato[] candidatos = null;

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {

            //lê a primeira linha para pegar o N(total de candidatos)
            String linhaN = leitor.readLine();
            if (linhaN == null)
                return null;
            int n = Integer.parseInt(linhaN.trim());
            candidatos = new Candidato[n];

            //Preenche o vetor lendo candidato por candidato(0 até n-1)
            for (int i = 0; i < n; i++) {
                //lê a linha do ****
                String linha = leitor.readLine();

                //se a linha lida fr separador ****, lemos a proxima linha para pegar o nome
                if (linha != null && linha.trim().equals("****")) {
                    linha = leitor.readLine();
                }

                String nome = linha.trim();
                String estado = leitor.readLine().trim();
                char sexo = leitor.readLine().trim().charAt(0);
                char sexoInteresse = leitor.readLine().trim().charAt(0);

                //lê a qtd de areas de interesse
                int qtdAreas = Integer.parseInt(leitor.readLine().trim());
                int[] areas = new int[qtdAreas];

                //lê cada codigo da area de interesse(uma por linha)
                for (int a = 0; a < areas.length; a++) {
                    areas[a] = Integer.parseInt(leitor.readLine().trim());
                }

                //cria o candidato com o id correspondente a sua posição i no vetor
                candidatos[i] = new Candidato(i, nome, estado, sexo, sexoInteresse, areas);
            }   
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro de formatação numérica no arquivo: " + e.getMessage());
        }
        return candidatos;
    }
}
