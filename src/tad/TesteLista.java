package tad;

public class TesteLista {
    public static void main(String[] args) {
        ListaIdentificadores lista = new ListaIdentificadores();

        System.out.println("add");
        //teste
        lista.add(15);
        lista.add(40);
        lista.add(8);

        System.out.println("toArray");
        //teste da lista com toArray
        int[] listaV = lista.toArray();
        for (int i = 0; i < listaV.length; i++) {
            System.out.print(listaV[i] + "\t");
        }
        System.out.println();

        System.out.println("contains");
        //teste do contain
        System.out.println("Tem 10?" + lista.contains(10));
        System.out.println("tem 40?" + lista.contains(40));

        System.out.println("isEmpty");
        //teste isEmpty
        System.out.println("lista está vazia?" + lista.isEmpty());

        System.out.println("size");
        //teste size
        System.out.println("Tamanho da lista: " + lista.size());

        System.out.println("clear");
        //teste clear
        lista.clear();
        System.out.println("lista está vazia?" + lista.isEmpty());
        System.out.println("Tamanho da lista: " + lista.size());


    }
}
