package app;

import java.util.Scanner;

import tad.Candidato;
import tad.ListaEncadeada;
import tad.ListaIdentificadores;

public class Main {

    private static Scanner teclado = new Scanner(System.in);
    private static ListaEncadeada<Candidato> candidatos; //todos os candidatos lidos do arquivo
    private static boolean paresCalculados = false; //só vira true depois da tarefa 1

    // le um numero inteiro do teclado (repete ate o usuario digitar certo)
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = teclado.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, digite um numero inteiro.");
            }
        }
    }

    // mostra o menu na tela
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== AGENCIA DE RELACIONAMENTO =====");
        System.out.println("1 - Inserir/atualizar o grau de interesse e calcular os pares");
        System.out.println("2 - Imprimir os potenciais companheiros de um candidato");
        System.out.println("3 - Imprimir os K candidatos com mais companheiros");
        System.out.println("4 - Imprimir candidatos sem nenhum companheiro");
        System.out.println("5 - Imprimir candidatos com interesse em uma area");
        System.out.println("6 - Imprimir candidatos que residem em um estado");
        System.out.println("0 - Sair");
    }

    // programa principal: le o arquivo e repete o menu ate o usuario sair
    // uso: java app.Main [caminho do arquivo]  (se nao informar, usa candidatos.txt)
    public static void main(String[] args) {
        String arquivo = "candidatos.txt";
        if (args.length > 0) {
            arquivo = args[0];
        }

        candidatos = LeitorCandidatos.carregarCandidatos(arquivo);
        if (candidatos == null) {
            System.out.println("Nao foi possivel carregar o arquivo " + arquivo);
            return;
        }
        System.out.println(candidatos.size() + " candidatos carregados de " + arquivo);

        int opcao = -1;
        while (opcao != 0) {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    tarefa1();
                    break;
                case 2:
                    tarefa2();
                    break;
                case 3:
                    tarefa3();
                    break;
                case 4:
                    tarefa4();
                    break;
                case 5:
                    tarefa5();
                    break;
                case 6:
                    tarefa6();
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    // Tarefa 1: le o grau de interesse e recalcula os companheiros de todos
    private static void tarefa1() {
        int grau = lerInteiro("Digite o grau de interesse desejado: ");
        if (grau < 0) {
            System.out.println("O grau de interesse nao pode ser negativo.");
            return;
        }
        Candidato.setGrauMinimo(grau);

        //para cada candidato, apaga os companheiros antigos e confere todos os outros
        int i = 0;
        while (i < candidatos.size()) {
            Candidato u = candidatos.get(i);
            u.limpaCompanheiros();

            int j = 0;
            while (j < candidatos.size()) {
                Candidato v = candidatos.get(j);
                if (u.verificaPotencialCompanheiro(v)) {
                    u.inserePotencialCompanheiro(v);
                }
                j++;
            }
            i++;
        }
        paresCalculados = true;
        System.out.println("Grau de interesse " + grau + " definido e pares calculados.");
    }

    // Tarefa 2: imprime o numero e os nomes dos companheiros de um candidato
    private static void tarefa2() {
        if (!paresCalculados) {
            System.out.println("Defina o grau de interesse na tarefa 1 antes.");
            return;
        }
        int id = lerInteiro("Digite o id do candidato (0 a " + (candidatos.size() - 1) + "): ");
        if (id < 0 || id >= candidatos.size()) {
            System.out.println("Id invalido.");
            return;
        }

        Candidato u = candidatos.get(id);
        ListaIdentificadores lista = u.listaCompanheiros();
        System.out.println("Candidato: " + u.getNome());
        System.out.println("Numero de potenciais companheiros: " + u.numeroCompanheiros());

        int i = 0;
        while (i < lista.size()) {
            Candidato companheiro = candidatos.get(lista.get(i)); //o id é a posição na lista
            System.out.println("- " + companheiro.getNome());
            i++;
        }
    }

    // Tarefa 3: imprime os K candidatos com mais companheiros
    private static void tarefa3() {
        if (!paresCalculados) {
            System.out.println("Defina o grau de interesse na tarefa 1 antes.");
            return;
        }
        int k = lerInteiro("Digite o valor de K: ");
        if (k <= 0) {
            System.out.println("K precisa ser maior que zero.");
            return;
        }
        if (k > candidatos.size()) {
            k = candidatos.size(); //se K for maior que o total, imprime todos
        }

        //guarda os ids que ja foram impressos
        ListaIdentificadores jaImpressos = new ListaIdentificadores();

        for (int posicao = 1; posicao <= k; posicao++) {
            Candidato melhor = null;

            //procura o candidato com mais companheiros que ainda nao foi impresso
            int i = 0;
            while (i < candidatos.size()) {
                Candidato atual = candidatos.get(i);
                if (!jaImpressos.contains(atual.getId())) {
                    if (melhor == null || atual.numeroCompanheiros() > melhor.numeroCompanheiros()) {
                        melhor = atual;
                    }
                }
                i++;
            }

            jaImpressos.add(melhor.getId());
            System.out.println(posicao + "o: " + melhor + " (" + melhor.numeroCompanheiros() + " companheiros)");
        }
    }

    // Tarefa 4: imprime os candidatos sem nenhum companheiro
    private static void tarefa4() {
        if (!paresCalculados) {
            System.out.println("Defina o grau de interesse na tarefa 1 antes.");
            return;
        }
        int encontrados = 0;
        int i = 0;
        while (i < candidatos.size()) {
            Candidato c = candidatos.get(i);
            if (c.numeroCompanheiros() == 0) {
                System.out.println(c);
                encontrados++;
            }
            i++;
        }
        if (encontrados == 0) {
            System.out.println("Todos os candidatos tem pelo menos um companheiro.");
        }
    }

    // Tarefa 5: imprime os candidatos com interesse em uma area
    private static void tarefa5() {
        System.out.println("Areas: 1-ESPORTES 2-ARTES 3-MUSICA 4-CINEMA 5-TECNOLOGIA 6-ANIMAIS 7-GASTRONOMIA 8-CIENCIAS");
        int area = lerInteiro("Digite o codigo da area (1 a 8): ");
        if (area < 1 || area > 8) {
            System.out.println("Area invalida.");
            return;
        }
        int encontrados = 0;
        int i = 0;
        while (i < candidatos.size()) {
            Candidato c = candidatos.get(i);
            if (c.temInteresse(area)) {
                System.out.println(c);
                encontrados++;
            }
            i++;
        }
        if (encontrados == 0) {
            System.out.println("Nenhum candidato com interesse nessa area.");
        }
    }

    // Tarefa 6: imprime os candidatos que moram em um estado
    private static void tarefa6() {
        System.out.print("Digite a sigla do estado: ");
        String sigla = teclado.nextLine().trim();
        if (sigla.length() == 0) {
            System.out.println("Sigla invalida.");
            return;
        }
        int encontrados = 0;
        int i = 0;
        while (i < candidatos.size()) {
            Candidato c = candidatos.get(i);
            if (c.reside(sigla)) {
                System.out.println(c);
                encontrados++;
            }
            i++;
        }
        if (encontrados == 0) {
            System.out.println("Nenhum candidato reside nesse estado.");
        }
    }
}
