package tad;

//areas de interesse e companheiros de cada candidato
public class ListaIdentificadores {
    // atributos
    private Celula head;
    private Celula tail;
    private int size;

    // classe celula
    class Celula {
        int item;
        Celula prox;

        // Construtor sentinela
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
    public ListaIdentificadores() {
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

    //verifica se a lista está vazia
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

    //retorna o id que esta na posicao i (começando em 0)
    public int get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Posicao invalida: " + i);
        }
        Celula atual = head.prox;
        int cont = 0;
        while (cont < i) {
            atual = atual.prox;
            cont++;
        }
        return atual.item;
    }

    //String com os ids da lista
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        Celula atual = head.prox;
        while (atual != null) {
            sb.append(atual.item);
            sb.append(" ");
            atual = atual.prox;
        }
        sb.append("]");
        return sb.toString();
    }
}
