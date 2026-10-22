package tad;

public class TesteListaEncadeada {
    public static void main(String[] args) {
        ListaEncadeada<String> nomes = new ListaEncadeada<String>();

        // lista recem criada
        System.out.println(nomes + " vazia? " + nomes.isEmpty()); // [ ] true

        // add e toString
        nomes.add("Ana");
        nomes.add("Bruno");
        nomes.add("Carla");
        System.out.println(nomes); // [ Ana Bruno Carla ]

        // get e size
        System.out.println("posicao 0: " + nomes.get(0)); // Ana
        System.out.println("posicao 1: " + nomes.get(1)); // Bruno
        System.out.println("posicao 2: " + nomes.get(2)); // Carla
        System.out.println("tamanho: " + nomes.size()); // 3

        // posicoes invalidas
        try {
            nomes.get(-1);
            System.out.println("nao lancou excecao (erro)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("get(-1): " + e.getMessage()); // Posicao invalida: -1
        }
        try {
            nomes.get(3);
            System.out.println("nao lancou excecao (erro)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("get(3): " + e.getMessage()); // Posicao invalida: 3
        }

        // clear
        nomes.clear();
        System.out.println(nomes + " vazia? " + nomes.isEmpty() + " tamanho: " + nomes.size()); // [ ] true 0

        // depois do clear a lista continua funcionando
        nomes.add("Diana");
        System.out.println(nomes); // [ Diana ]
    }
}
