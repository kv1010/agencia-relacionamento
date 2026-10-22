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

    // imprime id e nome de todos os candidatos, para o usuario saber qual id escolher
    private static void listarCandidatos() {
        System.out.println("Candidatos cadastrados (id - nome):");
        int i = 0;
        while (i < candidatos.size()) {
            System.out.println(candidatos.get(i)); //o toString do Candidato imprime "id - nome"
            i++;
        }
        System.out.println();
    }

    // imprime as siglas dos estados que aparecem no arquivo (sem repetir), para o usuario saber o que digitar
    private static void listarEstados() {
        ListaEncadeada<String> estados = new ListaEncadeada<String>();
        int i = 0;
        while (i < candidatos.size()) {
            String estado = candidatos.get(i).getEstado();

            //confere se a sigla ja esta na lista de estados
            boolean jaTem = false;
            int j = 0;
            while (j < estados.size()) {
                if (estados.get(j).equals(estado)) {
                    jaTem = true;
                }
                j++;
            }

            if (!jaTem && estado.length() > 0) {
                estados.add(estado);
            }
            i++;
        }
        System.out.println("Estados cadastrados: " + estados); //ex: [ MG SP ]
    }

    // le um texto do teclado que nao pode ficar vazio (repete ate o usuario digitar algo)
    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = teclado.nextLine().trim();
            if (linha.length() > 0) {
                return linha;
            }
            System.out.println("O valor nao pode ficar vazio.");
        }
    }

    // le a sigla de um estado (2 letras)
    private static String lerEstado(String mensagem) {
        while (true) {
            String sigla = lerTexto(mensagem);
            if (sigla.length() == 2) {
                return sigla.toUpperCase();
            }
            System.out.println("Sigla invalida, digite 2 letras (ex: MG).");
        }
    }

    // le um sexo: F ou M (se aceitaI for true, tambem aceita I = qualquer sexo)
    private static char lerSexo(String mensagem, boolean aceitaI) {
        while (true) {
            String texto = lerTexto(mensagem).toUpperCase();
            if (texto.length() == 1) {
                char c = texto.charAt(0);
                if (c == 'F' || c == 'M' || (aceitaI && c == 'I')) {
                    return c;
                }
            }
            if (aceitaI) {
                System.out.println("Valor invalido, digite F, M ou I.");
            } else {
                System.out.println("Valor invalido, digite F ou M.");
            }
        }
    }

    // le as areas de interesse (codigos de 1 a 8 separados por espaco)
    // devolve null se algum codigo for invalido ou se nao informar nenhuma area
    private static ListaIdentificadores lerAreas() {
        System.out.println("Areas: 1-ESPORTES 2-ARTES 3-MUSICA 4-CINEMA 5-TECNOLOGIA 6-ANIMAIS 7-GASTRONOMIA 8-CIENCIAS");
        System.out.print("Digite os codigos das areas separados por espaco: ");
        String linha = teclado.nextLine();

        Scanner leitorLinha = new Scanner(linha);
        ListaIdentificadores areas = new ListaIdentificadores();
        while (leitorLinha.hasNext()) {
            String token = leitorLinha.next();
            int area;
            try {
                area = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                System.out.println("Area invalida: " + token);
                return null;
            }
            if (area < 1 || area > 8) {
                System.out.println("Area invalida: " + area + " (use codigos de 1 a 8).");
                return null;
            }
            if (!areas.contains(area)) { //ignora area repetida
                areas.add(area);
            }
        }
        if (areas.isEmpty()) {
            System.out.println("Informe pelo menos uma area.");
            return null;
        }
        return areas;
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
        System.out.println("7 - Cadastrar um novo candidato");
        System.out.println("8 - Atualizar os dados de um candidato");
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
                case 7:
                    cadastrarCandidato();
                    break;
                case 8:
                    atualizarCandidato();
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    /*
     * COMMIT 21
     * Título: Cria a classe Main com leitura do arquivo e menu interativo
     * (neste commit deixe cada case de tarefa só com um println "Tarefa X ainda não implementada";
     *  as chamadas tarefa1() a tarefa6() entram uma por vez nos commits 23, 25, 27, 29, 31 e 33)
     */

    /*
     * COMMIT 22
     * Título: Testa o menu da Main: opção inválida, texto no lugar de número e sair
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução do programa com entradas inválidas no menu e saída pela opção 0, conferindo as mensagens na tela.
     * Relatório de teste:
     *   arquivo candidatos.txt de exemplo -> esperado "3 candidatos carregados de candidatos.txt" -> obtido igual (OK)
     *   entrada "abc" no menu -> esperado "Valor invalido, digite um numero inteiro." e o menu de novo -> obtido igual (OK)
     *   entrada 9 no menu -> esperado "Opcao invalida." -> obtido igual (OK)
     *   entrada 0 -> esperado "Encerrando o programa." e fim da execução -> obtido igual (OK)
     */

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

    /*
     * COMMIT 23
     * Título: Implementa tarefa 1: definição do grau de interesse e cálculo dos pares
     */

    /*
     * COMMIT 24
     * Título: Testa a tarefa 1: grau válido, negativo e texto
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução da opção 1 do menu com grau válido, negativo e texto, e conferência do efeito na tarefa 2.
     * Relatório de teste:
     *   grau 3 -> esperado "Grau de interesse 3 definido e pares calculados." -> obtido igual (OK)
     *   grau -5 -> esperado "O grau de interesse nao pode ser negativo." e voltar ao menu -> obtido igual (OK)
     *   grau "xyz" -> esperado "Valor invalido, digite um numero inteiro." e pedir de novo -> obtido igual (OK)
     *   tarefa 2 antes da tarefa 1 -> esperado "Defina o grau de interesse na tarefa 1 antes." -> obtido igual (OK)
     */

    // Tarefa 2: imprime o numero e os nomes dos companheiros de um candidato
    private static void tarefa2() {
        if (!paresCalculados) {
            System.out.println("Defina o grau de interesse na tarefa 1 antes.");
            return;
        }

        //mostra id e nome de todos antes de pedir o id, para a escolha ser possivel
        listarCandidatos();

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

    /*
     * COMMIT 25
     * Título: Implementa tarefa 2: número e nomes dos potenciais companheiros
     */

    /*
     * COMMIT 26
     * Título: Testa a tarefa 2: candidato com e sem companheiros e id inválido
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução da opção 2 com grau 3 no arquivo de exemplo, para candidatos com companheiro, sem companheiro e com id fora do intervalo.
     * Relatório de teste (grau 3):
     *   id 0 (Ana) -> esperado 1 companheiro: Bruno Lima -> obtido igual (OK)
     *   id 1 (Bruno) -> esperado 1 companheiro: Ana Souza (relação simétrica) -> obtido igual (OK)
     *   id 2 (Carla, mora em SP) -> esperado 0 companheiros -> obtido igual (OK)
     *   id 9 -> esperado "Id invalido." -> obtido igual (OK)
     */

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

        //guarda os ids que ja foram impressos (sem usar vetor)
        ListaIdentificadores jaImpressos = new ListaIdentificadores();

        for (int posicao = 1; posicao <= k; posicao++) {
            Candidato melhor = null;

            //procura o candidato com mais companheiros que ainda nao foi impresso
            //como a lista esta em ordem de id, usar > garante que no empate fica o menor id
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

    /*
     * COMMIT 27
     * Título: Implementa tarefa 3: K candidatos com mais companheiros
     */

    /*
     * COMMIT 28
     * Título: Testa a tarefa 3: desempate pelo menor id, K maior que o total e K inválido
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução da opção 3 com grau 3 no arquivo de exemplo (Ana e Bruno empatados com 1 companheiro, Carla com 0), variando o valor de K.
     * Relatório de teste (grau 3):
     *   K = 1 -> esperado Ana (empate com Bruno, entra o menor id) -> obtido "1o: 0 - Ana Souza (1 companheiros)" (OK)
     *   K = 2 -> esperado Ana e Bruno -> obtido igual (OK)
     *   K = 10 (maior que o total) -> esperado os 3 candidatos, Carla por último -> obtido igual (OK)
     *   K = 0 -> esperado "K precisa ser maior que zero." -> obtido igual (OK)
     */

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

    /*
     * COMMIT 29
     * Título: Implementa tarefa 4: candidatos sem companheiros
     */

    /*
     * COMMIT 30
     * Título: Testa a tarefa 4: candidatos sem companheiros com graus diferentes
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução da opção 4 no arquivo de exemplo com grau 3 e com grau 6, conferindo que um grau maior elimina os pares.
     * Relatório de teste:
     *   grau 3 -> esperado só a Carla (mora em SP) -> obtido "2 - Carla Dias" (OK)
     *   grau 6 (maior que o grau em comum 4 de Ana e Bruno) -> esperado os 3 candidatos -> obtido igual (OK)
     */

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

    /*
     * COMMIT 31
     * Título: Implementa tarefa 5: candidatos com interesse em uma área
     */

    /*
     * COMMIT 32
     * Título: Testa a tarefa 5: área com candidatos, sem candidatos e inválida
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução da opção 5 no arquivo de exemplo (Ana: 1, 3, 5; Bruno: 3, 5, 2; Carla: 2) com várias áreas.
     * Relatório de teste:
     *   área 3 -> esperado Ana e Bruno -> obtido "0 - Ana Souza" e "1 - Bruno Lima" (OK)
     *   área 2 -> esperado Bruno e Carla -> obtido "1 - Bruno Lima" e "2 - Carla Dias" (OK)
     *   área 8 -> esperado "Nenhum candidato com interesse nessa area." -> obtido igual (OK)
     *   área 9 -> esperado "Area invalida." -> obtido igual (OK)
     */

    // Tarefa 6: imprime os candidatos que moram em um estado
    private static void tarefa6() {
        listarEstados(); //mostra os estados do arquivo antes de pedir a sigla
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

    /*
     * COMMIT 33
     * Título: Implementa tarefa 6: candidatos que residem em um estado
     */

    /*
     * COMMIT 34
     * Título: Testa a tarefa 6: siglas em maiúscula, minúscula e estado sem candidatos
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução da opção 6 no arquivo de exemplo (Ana e Bruno em MG, Carla em SP), conferindo que maiúsculas e minúsculas não fazem diferença.
     * Relatório de teste:
     *   sigla "mg" -> esperado Ana e Bruno -> obtido "0 - Ana Souza" e "1 - Bruno Lima" (OK)
     *   sigla "SP" -> esperado Carla -> obtido "2 - Carla Dias" (OK)
     *   sigla "rj" -> esperado "Nenhum candidato reside nesse estado." -> obtido igual (OK)
     */

    // Opcao 7: cadastra um novo candidato (fica so na memoria, o candidatos.txt nao e alterado)
    private static void cadastrarCandidato() {
        String nome = lerTexto("Nome: ");
        String estado = lerEstado("Sigla do estado (ex: MG): ");
        char sexo = lerSexo("Sexo (F/M): ", false);
        char sexoInteresse = lerSexo("Sexo do potencial companheiro (F/M/I): ", true);
        ListaIdentificadores areas = lerAreas();
        if (areas == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        //o id e a posicao na lista, entao o novo candidato recebe o proximo numero
        Candidato novo = new Candidato(candidatos.size(), nome, estado, sexo, sexoInteresse, areas);
        candidatos.add(novo);

        paresCalculados = false; //os pares antigos nao valem mais
        System.out.println("Candidato cadastrado: " + novo);
        System.out.println("Execute a opcao 1 novamente para recalcular os pares.");
    }

    // Opcao 8: atualiza os dados de um candidato ja cadastrado
    private static void atualizarCandidato() {
        listarCandidatos();
        int id = lerInteiro("Digite o id do candidato (0 a " + (candidatos.size() - 1) + "): ");
        if (id < 0 || id >= candidatos.size()) {
            System.out.println("Id invalido.");
            return;
        }
        Candidato c = candidatos.get(id);

        System.out.println("Dados atuais: " + c + " | estado " + c.getEstado() + " | sexo " + c.getSexo()
                + " | procura " + c.getSexoInteresse() + " | areas " + c.getAreas());
        System.out.println("1 - Nome");
        System.out.println("2 - Estado");
        System.out.println("3 - Sexo");
        System.out.println("4 - Sexo do potencial companheiro");
        System.out.println("5 - Areas de interesse");
        System.out.println("0 - Cancelar");
        int campo = lerInteiro("Qual campo deseja alterar? ");

        switch (campo) {
            case 1:
                c.setNome(lerTexto("Novo nome: "));
                break;
            case 2:
                c.setEstado(lerEstado("Nova sigla do estado: "));
                break;
            case 3:
                c.setSexo(lerSexo("Novo sexo (F/M): ", false));
                break;
            case 4:
                c.setSexoInteresse(lerSexo("Novo sexo do potencial companheiro (F/M/I): ", true));
                break;
            case 5:
                ListaIdentificadores novasAreas = lerAreas();
                if (novasAreas == null) {
                    System.out.println("Atualizacao cancelada.");
                    return;
                }
                c.setAreas(novasAreas);
                break;
            case 0:
                System.out.println("Atualizacao cancelada.");
                return;
            default:
                System.out.println("Opcao invalida.");
                return;
        }

        paresCalculados = false; //os pares antigos nao valem mais
        System.out.println("Candidato atualizado: " + c + " | estado " + c.getEstado() + " | sexo " + c.getSexo()
                + " | procura " + c.getSexoInteresse() + " | areas " + c.getAreas());
        System.out.println("Execute a opcao 1 novamente para recalcular os pares.");
    }

    /*
     * COMMIT 40
     * Título: Adiciona opções 7 e 8 no menu: cadastrar e atualizar candidato usando o construtor e os setters do TAD
     */

    /*
     * COMMIT 39
     * Título: Testa a aplicação completa com arquivo de exemplo e com arquivo gerado
     * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Teste de integração do menu com as 6 tarefas no candidatos.txt de exemplo e em um arquivo de 40 candidatos gerado pelo GeradorCandidatos, conferindo os resultados com um cálculo feito à parte.
     * Relatório de teste:
     *   exemplo, grau 3 -> esperado Ana e Bruno companheiros e Carla sem companheiro -> obtido igual (OK)
     *   gerado (40 candidatos), grau 4, tarefa 3 com K = 3 -> esperado ids 29 e 34 (11 companheiros, empate pelo menor id) e depois o id 1 (8 companheiros) -> obtido igual ao cálculo conferido à parte (OK)
     *   gerado, grau 4, tarefa 4 -> esperado 11 candidatos sem companheiro (ids 2, 3, 5, 7, 14, 19, 22, 25, 31, 32 e 39) -> obtido igual (OK)
     *   relação simétrica (se A é companheiro de B, B é de A) conferida no arquivo gerado -> OK
     *   tarefas 5 e 6 no arquivo gerado -> esperado só candidatos da área/estado pedido -> obtido igual (OK)
     */
}
