package tad;

public class ListaEncadeada<T> {
    // atributos
    private Celula head; 
    private Celula tail; 
    private int size;

    // celula da lista
    private class Celula {
        T item;
        Celula prox;

        public Celula(T item) {
            this.item = item;
            this.prox = null;
        }
    }

    // cria lista vazia
    public ListaEncadeada() {
        head = new Celula(null);
        tail = head;
        size = 0;
    }

    // insere no fim da lista
    public void add(T item) {
        if (item != null) {
            tail.prox = new Celula(item);
            tail = tail.prox;
            size++;
        }
    }

    // retorna o item que esta na posicao i (comecando em 0)
    public T get(int i) {
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

    // retorna a quantidade de itens
    public int size() {
        return size;
    }

    // verifica se a lista esta vazia
    public boolean isEmpty() {
        return size == 0;
    }

    // esvazia a lista
    public void clear() {
        head.prox = null;
        tail = head;
        size = 0;
    }

    //String com os itens da lista
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
