package tad;

// Lista encadeada generica com sentinela e tail
// Feita no molde da LEDS<T> do professor
// Usada para guardar os candidatos (e tambem os nomes lidos pelo gerador)
public class ListaEncadeada<T> {
    // atributos
    private Celula head; // sentinela (nao guarda item de verdade)
    private Celula tail; // aponta para a ultima celula
    private int size;

    // celula da lista (simplesmente encadeada)
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

    /*
     * COMMIT 01
     * Título: Cria estrutura da ListaEncadeada com célula e sentinela
     */

    // insere no fim da lista
    public void add(T item) {
        if (item != null) {
            tail.prox = new Celula(item);
            tail = tail.prox;
            size++;
        }
    }

    /*
     * COMMIT 02
     * Título: Implementa inserção no fim (add) na ListaEncadeada
     */

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

    /*
     * COMMIT 03
     * Título: Implementa acesso por posição (get) na ListaEncadeada
     */

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

    /*
     * COMMIT 04
     * Título: Implementa size, isEmpty e clear na ListaEncadeada
     */

    // monta uma String com os itens da lista
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

    /*
     * COMMIT 05
     * Título: Implementa toString na ListaEncadeada
     */
}
