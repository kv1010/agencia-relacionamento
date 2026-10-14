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

    //insere no fim da lista
    public void add(int id) {
        Celula nova = new Celula(id);
        tail.prox = nova;
        tail = nova;
        size++;
    }

    //retorna o tamanho da lista
    public int size() {
        return size;
    }

    //verifica se a lista está vazia ou não
    public boolean isEmpty() {
        return head.prox == null;
    }

    //verifica se um id ja existe na lista
    public boolean contains(int id) {
        Celula atual = head.prox;
        while (atual != null) {
            if (atual.item == id)
                return true;
            atual = atual.prox;
        }
        return false;
    }

    //limpar a lista
    public void clear() {
        head.prox = null;
        tail = head;
        size = 0;
    }

    //Copia os elementos da lista pra um vetor
    public int[] toArray() {
        int[] vetor = new int[size]; //vetor com tamanho size
        Celula atual = head.prox;
        int i = 0;
        while (atual != null) {
            vetor[i] = atual.item;//vetor[i] recebe o item da celula atual 
            i++;
            atual = atual.prox;
        }
        return vetor;
    }

}