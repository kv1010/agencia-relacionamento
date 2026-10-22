package app;

import tad.Candidato;
import tad.ListaEncadeada;

// Teste do LeitorCandidatos: le o arquivo e imprime o que foi carregado
// uso: java app.TesteLeitor [arquivo]  (se nao informar, usa candidatos.txt)
public class TesteLeitor {
    public static void main(String[] args) {
        String arquivo = "candidatos.txt";
        if (args.length > 0) {
            arquivo = args[0];
        }

        ListaEncadeada<Candidato> candidatos = LeitorCandidatos.carregarCandidatos(arquivo);
        if (candidatos == null) {
            System.out.println("Nao foi possivel carregar " + arquivo);
            return;
        }

        System.out.println("Candidatos carregados: " + candidatos.size());
        for (int i = 0; i < candidatos.size(); i++) {
            Candidato c = candidatos.get(i);
            System.out.println(c + " | " + c.getEstado() + " | sexo " + c.getSexo()
                    + " | procura " + c.getSexoInteresse() + " | areas " + c.getAreas());
        }
    }
}

/*
 * COMMIT 14
 * Título: Testa LeitorCandidatos com arquivo de exemplo e arquivos com erro
 * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Criação do TesteLeitor, que carrega um arquivo e imprime id, nome, estado, sexos e áreas de cada candidato, usando o candidatos.txt de exemplo e arquivos propositalmente errados.
 * Relatório de teste:
 *   candidatos.txt de exemplo (Ana, Bruno e Carla) -> esperado 3 candidatos com ids 0, 1 e 2 e áreas lidas na ordem do arquivo -> obtido "0 - Ana Souza ... [ 1 3 5 ]", "1 - Bruno Lima ... [ 3 5 2 ]", "2 - Carla Dias ... [ 2 ]" (OK)
 *   arquivo incompleto (faltando uma área) -> esperado mensagem de arquivo incompleto e retorno null -> obtido "O arquivo está incompleto ou fora do formato esperado." (OK)
 *   arquivo com texto no lugar do número de áreas -> esperado erro de formatação numérica e retorno null -> obtido "Erro de formatação numérica no arquivo" (OK)
 *   arquivo que não existe -> esperado erro de leitura e retorno null -> obtido "Erro ao ler o arquivo: ... (No such file or directory)" (OK)
 */
