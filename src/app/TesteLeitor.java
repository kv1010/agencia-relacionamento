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
