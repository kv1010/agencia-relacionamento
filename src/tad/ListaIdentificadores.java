package tad;

public class ListaIdentificadores {
    // atributos
    private Celula head;
    private Celula tail;
    private int size;

    // classe celula
    class Celula {
        int item;
        Celula prox;

        // Construtor vazio (para a sentinela)
        public Celula() {
            this.item = 0;
            this.prox = null;
        }

        // construtor celula
        public Celula(int item) {
            this.item = item;
            this.prox = null;
        }
    }

    // Construtor
    ListaIdentificadores() {
        head = new Celula();
        tail = head;
        size = 0;
    }


}