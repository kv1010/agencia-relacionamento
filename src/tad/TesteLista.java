package tad;

public class TesteLista {
    public static void main(String[] args) {
        ListaIdentificadores lista = new ListaIdentificadores();

        //teste
        lista.add(15);
        lista.add(40);
        lista.add(8);

        ListaIdentificadores.Celula atual = lista.head.prox;
        while (atual!= null) {
            System.out.println(atual.item);
            atual = atual.prox;
        }
        System.out.println();

        System.out.println(lista.size());

    }
}
