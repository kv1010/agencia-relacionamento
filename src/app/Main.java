package app;

import java.util.Scanner;

import tad.Candidato;
import tad.ListaEncadeada;
import tad.ListaIdentificadores;

public class Main {

    private static final int GRAU_MAXIMO = 15; //soma de todos os pesos (1+2+1+1+3+2+2+3)

    private static Scanner teclado = new Scanner(System.in);
    private static ListaEncadeada<Candidato> candidatos; //todos os candidatos lidos do arquivo
    private static boolean paresCalculados = false; //só vira true depois da tarefa 1

    // ------------------------------------------------------------------
    // LEITURA DE DADOS (cada leitura mostra ao usuario o que pode digitar)
    // ------------------------------------------------------------------

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

    // le a sigla de um estado (2 letras); aceita um estado que ainda nao esta cadastrado
    private static String lerEstado(String mensagem) {
        while (true) {
            String sigla = lerTexto(mensagem);
            if (sigla.length() == 2 && Character.isLetter(sigla.charAt(0)) && Character.isLetter(sigla.charAt(1))) {
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
        System.out.println("Areas: " + textoAreas());
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

    // ------------------------------------------------------------------
    // LISTAGENS AUXILIARES (mostradas antes de pedir um dado ao usuario)
    // ------------------------------------------------------------------

    // texto com os codigos das areas
    private static String textoAreas() {
        return "1-ESPORTES 2-ARTES 3-MUSICA 4-CINEMA 5-TECNOLOGIA 6-ANIMAIS 7-GASTRONOMIA 8-CIENCIAS";
    }

    // devolve o nome de uma area a partir do codigo
    private static String nomeArea(int area) {
        switch (area) {
            case 1:
                return "ESPORTES";
            case 2:
                return "ARTES";
            case 3:
                return "MUSICA";
            case 4:
                return "CINEMA";
            case 5:
                return "TECNOLOGIA";
            case 6:
                return "ANIMAIS";
            case 7:
                return "GASTRONOMIA";
            default:
                return "CIENCIAS";
        }
    }

    // monta um texto com o nome das areas de uma lista (ex: ESPORTES, MUSICA)
    private static String textoNomesAreas(ListaIdentificadores areas) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < areas.size()) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(nomeArea(areas.get(i)));
            i++;
        }
        return sb.toString();
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

    // confere se algum candidato ja reside na sigla informada
    private static boolean estadoCadastrado(String sigla) {
        int i = 0;
        while (i < candidatos.size()) {
            if (candidatos.get(i).reside(sigla)) {
                return true;
            }
            i++;
        }
        return false;
    }

    // imprime as siglas dos estados que aparecem nos candidatos (sem repetir), para o usuario saber o que digitar
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

    // imprime todos os dados de um candidato (usado no perfil e na alteracao)
    private static void mostrarDados(Candidato c) {
        System.out.println("Id: " + c.getId());
        System.out.println("Nome: " + c.getNome());
        System.out.println("Estado: " + c.getEstado());
        System.out.println("Sexo: " + c.getSexo());
        System.out.println("Sexo do potencial companheiro: " + c.getSexoInteresse());
        System.out.println("Areas de interesse: " + textoNomesAreas(c.getAreas()));
    }

    // imprime o numero e os nomes dos potenciais companheiros de um candidato
    private static void imprimirCompanheiros(Candidato u) {
        ListaIdentificadores lista = u.listaCompanheiros();
        System.out.println("Candidato: " + u.getNome());
        System.out.println("Numero de potenciais companheiros: " + u.numeroCompanheiros());

        int i = 0;
        while (i < lista.size()) {
            Candidato companheiro = candidatos.get(lista.get(i)); //o id é a posição na lista
            System.out.println("- " + companheiro);
            i++;
        }
    }

    // ------------------------------------------------------------------
    // MENU PRINCIPAL
    // ------------------------------------------------------------------

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
        System.out.println("8 - Imprimir todos os candidatos");
        System.out.println("9 - Abrir o perfil de um candidato (ver e alterar dados)");
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
                    imprimirCandidatos();
                    break;
                case 9:
                    perfilCandidato();
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    // ------------------------------------------------------------------
    // TAREFAS 1 A 6
    // ------------------------------------------------------------------

    // Tarefa 1: le o grau de interesse e recalcula os companheiros de todos
    private static void tarefa1() {
        int atual = Candidato.getGrauMinimo();
        System.out.println("Grau de interesse atual: " + atual);
        System.out.println("O grau em comum vai de 0 ate " + GRAU_MAXIMO + " (soma dos pesos de todas as areas).");
        int grau = lerInteiro("Digite o grau de interesse desejado (0 a " + GRAU_MAXIMO + "): ");
        if (grau < 0) {
            System.out.println("O grau de interesse nao pode ser negativo.");
            return;
        }
        if (grau > GRAU_MAXIMO) {
            System.out.println("Aviso: nenhum par tem grau em comum maior que " + GRAU_MAXIMO + ", entao nao havera companheiros.");
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
        if (candidatos.isEmpty()) {
            System.out.println("Nenhum candidato cadastrado.");
            return;
        }

        //mostra id e nome de todos antes de pedir o id, para a escolha ser possivel
        listarCandidatos();

        int id = lerInteiro("Digite o id do candidato (0 a " + (candidatos.size() - 1) + "): ");
        if (id < 0 || id >= candidatos.size()) {
            System.out.println("Id invalido.");
            return;
        }
        imprimirCompanheiros(candidatos.get(id));
    }

    // Tarefa 3: imprime os K candidatos com mais companheiros
    private static void tarefa3() {
        if (!paresCalculados) {
            System.out.println("Defina o grau de interesse na tarefa 1 antes.");
            return;
        }
        System.out.println("Total de candidatos: " + candidatos.size());
        int k = lerInteiro("Digite o valor de K (1 a " + candidatos.size() + "): ");
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
        System.out.println("Areas: " + textoAreas());
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
        listarEstados(); //mostra os estados cadastrados antes de pedir a sigla
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

    // ------------------------------------------------------------------
    // CADASTRO, LISTAGEM E PERFIL DOS CANDIDATOS
    // ------------------------------------------------------------------

    // Opcao 7: cadastra um novo candidato (fica so na memoria, o candidatos.txt nao e alterado)
    private static void cadastrarCandidato() {
        System.out.println("--- Cadastro de novo candidato ---");
        String nome = lerTexto("Nome: ");

        //mostra os estados que ja existem; se a sigla digitada for nova, ela passa a ser cadastrada
        listarEstados();
        String estado = lerEstado("Sigla do estado (uma da lista ou um estado novo): ");
        boolean estadoNovo = !estadoCadastrado(estado);
        if (estadoNovo) {
            System.out.println("O estado " + estado + " ainda nao estava cadastrado e sera cadastrado com este candidato.");
        }

        char sexo = lerSexo("Sexo (F/M): ", false);
        char sexoInteresse = lerSexo("Sexo do potencial companheiro (F/M/I, I = qualquer sexo): ", true);

        //repete ate o usuario informar areas validas
        ListaIdentificadores areas = lerAreas();
        while (areas == null) {
            areas = lerAreas();
        }

        //o id e a posicao na lista, entao o novo candidato recebe o proximo numero
        Candidato novo = new Candidato(candidatos.size(), nome, estado, sexo, sexoInteresse, areas);
        candidatos.add(novo);

        paresCalculados = false; //os pares antigos nao valem mais
        System.out.println("Candidato cadastrado: " + novo);
        if (estadoNovo) {
            listarEstados(); //mostra a lista ja com o estado novo
        }
        System.out.println("Execute a opcao 1 novamente para recalcular os pares.");
    }

    // Opcao 8: imprime todos os candidatos com os dados principais
    private static void imprimirCandidatos() {
        if (candidatos.isEmpty()) {
            System.out.println("Nenhum candidato cadastrado.");
            return;
        }
        System.out.println("Candidatos cadastrados (" + candidatos.size() + "):");
        int i = 0;
        while (i < candidatos.size()) {
            Candidato c = candidatos.get(i);
            System.out.println(c + " | " + c.getEstado() + " | sexo " + c.getSexo()
                    + " | procura " + c.getSexoInteresse() + " | areas: " + textoNomesAreas(c.getAreas()));
            i++;
        }
    }

    // Opcao 9: lista os candidatos, pede o id e abre o menu do perfil dele
    private static void perfilCandidato() {
        if (candidatos.isEmpty()) {
            System.out.println("Nenhum candidato cadastrado.");
            return;
        }
        listarCandidatos();
        int id = lerInteiro("Digite o id do candidato (0 a " + (candidatos.size() - 1) + "): ");
        if (id < 0 || id >= candidatos.size()) {
            System.out.println("Id invalido.");
            return;
        }
        Candidato c = candidatos.get(id);

        int opcao = -1;
        while (opcao != 0) {
            System.out.println();
            System.out.println("===== PERFIL DO CANDIDATO =====");
            mostrarDados(c);
            if (paresCalculados) {
                System.out.println("Potenciais companheiros: " + c.numeroCompanheiros() + " (grau " + Candidato.getGrauMinimo() + ")");
            } else {
                System.out.println("Potenciais companheiros: ainda nao calculados (use a opcao 1 do menu principal)");
            }
            System.out.println("-----------------------------");
            System.out.println("1 - Alterar dados do candidato");
            System.out.println("2 - Ver potenciais companheiros");
            System.out.println("0 - Voltar ao menu principal");
            opcao = lerInteiro("Escolha uma opcao do perfil: ");

            switch (opcao) {
                case 1:
                    alterarDados(c);
                    break;
                case 2:
                    if (paresCalculados) {
                        imprimirCompanheiros(c);
                    } else {
                        System.out.println("Defina o grau de interesse na tarefa 1 antes.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    // menu de alteracao: fica aberto ate o usuario escolher 0 (concluir), podendo alterar varios campos
    private static void alterarDados(Candidato c) {
        boolean alterou = false;

        int campo = -1;
        while (campo != 0) {
            System.out.println();
            System.out.println("--- Alterar dados ---");
            mostrarDados(c);
            System.out.println("1 - Nome");
            System.out.println("2 - Estado");
            System.out.println("3 - Sexo");
            System.out.println("4 - Sexo do potencial companheiro");
            System.out.println("5 - Areas de interesse");
            System.out.println("0 - Concluir alteracoes");
            campo = lerInteiro("Qual campo deseja alterar? ");

            switch (campo) {
                case 1:
                    c.setNome(lerTexto("Novo nome: "));
                    alterou = true;
                    System.out.println("Nome atualizado.");
                    break;
                case 2:
                    listarEstados();
                    String estado = lerEstado("Nova sigla do estado (uma da lista ou um estado novo): ");
                    boolean estadoNovo = !estadoCadastrado(estado);
                    if (estadoNovo) {
                        System.out.println("O estado " + estado + " ainda nao estava cadastrado e sera cadastrado com esta alteracao.");
                    }
                    c.setEstado(estado);
                    alterou = true;
                    System.out.println("Estado atualizado.");
                    if (estadoNovo) {
                        listarEstados(); //mostra a lista ja com o estado novo
                    }
                    break;
                case 3:
                    c.setSexo(lerSexo("Novo sexo (F/M): ", false));
                    alterou = true;
                    System.out.println("Sexo atualizado.");
                    break;
                case 4:
                    c.setSexoInteresse(lerSexo("Novo sexo do potencial companheiro (F/M/I, I = qualquer sexo): ", true));
                    alterou = true;
                    System.out.println("Sexo do potencial companheiro atualizado.");
                    break;
                case 5:
                    ListaIdentificadores novasAreas = lerAreas();
                    if (novasAreas == null) {
                        System.out.println("Areas nao alteradas.");
                    } else {
                        c.setAreas(novasAreas);
                        alterou = true;
                        System.out.println("Areas atualizadas.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }

        if (alterou) {
            paresCalculados = false; //os pares antigos nao valem mais
            System.out.println("Alteracoes concluidas: " + c);
            System.out.println("Execute a opcao 1 novamente para recalcular os pares.");
        } else {
            System.out.println("Nenhuma alteracao feita.");
        }
    }
}

/*
 * COMMIT 41
 * Título: Mostra os estados cadastrados e registra estado novo no cadastro e na alteração
 *
 * COMMIT 42
 * Título: Torna a alteração de dados um menu em laço que só termina na opção 0 (concluir)
 *
 * COMMIT 43
 * Título: Adiciona a opção 8 (imprimir todos os candidatos) e a opção 9 (perfil do candidato com menu)
 * (a antiga opção 8 "Atualizar os dados" passou para dentro do perfil)
 *
 * COMMIT 44
 * Título: Mostra a faixa de valores válidos nos pedidos de grau (0 a 15) e de K (1 a total)
 *
 * COMMIT 45
 * Título: Testa cadastro com estado novo, perfil, alteração em laço, impressão e top K
 * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Execução do menu com o arquivo de exemplo: perfil do candidato 0, alteração de estado (novo), sexo (inválido e válido) e sexo de interesse numa mesma sessão, cadastro de candidato com estado novo e área inválida, opção 8 e tarefa 3.
 * Relatório de teste:
 *   opção 9 com id 0 -> esperado lista de candidatos antes do pedido e perfil com áreas por nome -> obtido igual (OK)
 *   alterar estado para "rj" (novo) -> esperado aviso de estado novo e "Estados cadastrados: [ RJ MG SP ]" -> obtido igual (OK)
 *   sexo "x" -> esperado "Valor invalido, digite F ou M." e pedir de novo -> obtido igual (OK)
 *   alterar vários campos -> esperado o menu de alteração continuar aberto até a opção 0 -> obtido igual (OK)
 *   após alterar -> esperado pares invalidados e "Defina o grau de interesse na tarefa 1 antes." no perfil -> obtido igual (OK)
 *   cadastro com estado "ba" e área 9 -> esperado aviso de estado novo, pedir as áreas de novo e lista com BA -> obtido "[ RJ MG SP BA ]" (OK)
 *   opção 8 -> esperado os 4 candidatos com estado, sexos e áreas -> obtido igual (OK)
 *   tarefa 3 -> esperado mostrar "Total de candidatos: 4" e "(1 a 4)" no pedido do K -> obtido igual (OK)
 */