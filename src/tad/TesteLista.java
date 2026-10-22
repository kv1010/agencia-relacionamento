package tad;

// Teste da ListaIdentificadores
public class TesteLista {
    public static void main(String[] args) {
        ListaIdentificadores lista = new ListaIdentificadores();

        System.out.println("add");
        //teste
        lista.add(15);
        lista.add(40);
        lista.add(8);

        System.out.println("get");
        //teste da lista com get (percorrendo com while)
        int i = 0;
        while (i < lista.size()) {
            System.out.print(lista.get(i) + "\t"); // 15 40 8
            i++;
        }
        System.out.println();

        System.out.println("toString");
        System.out.println(lista); // [ 15 40 8 ]

        System.out.println("contains");
        //teste do contain
        System.out.println("Tem 10? " + lista.contains(10)); // false
        System.out.println("tem 40? " + lista.contains(40)); // true

        System.out.println("isEmpty");
        //teste isEmpty
        System.out.println("lista está vazia? " + lista.isEmpty()); // false

        System.out.println("size");
        //teste size
        System.out.println("Tamanho da lista: " + lista.size()); // 3

        System.out.println("posicao invalida");
        try {
            lista.get(3);
            System.out.println("nao lancou excecao (erro)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("lancou excecao (certo): " + e.getMessage()); // Posicao invalida: 3
        }

        System.out.println("clear");
        //teste clear
        lista.clear();
        System.out.println("lista está vazia? " + lista.isEmpty()); // true
        System.out.println("Tamanho da lista: " + lista.size()); // 0
    }
}

/*
 * COMMIT 10
 * Título: Atualiza TesteLista para usar get e toString no lugar do toArray
 * Descrição: Relatório: DD/MM/AAAA ; X h ; Realizar os testes ; Atualização do TesteLista da ListaIdentificadores: percorre a lista com get dentro de um while e testa toString, contains, isEmpty, size, posição inválida e clear.
 * Relatório de teste:
 *   add 15, 40, 8 e percorrer com get -> esperado "15 40 8" -> obtido igual (OK)
 *   toString -> esperado "[ 15 40 8 ]" -> obtido igual (OK)
 *   contains(10) e contains(40) -> esperado false e true -> obtido igual (OK)
 *   isEmpty e size -> esperado false e 3 -> obtido igual (OK)
 *   get(3) -> esperado IndexOutOfBoundsException -> lançada (OK)
 *   clear -> esperado vazia true e tamanho 0 -> obtido igual (OK)
 */
