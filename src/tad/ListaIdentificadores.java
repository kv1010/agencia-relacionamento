package tad;

// Lista de identificadores (inteiros) com sentinela e tail
// Usada para as areas de interesse e para os companheiros de cada candidato
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

    // Construtor (agora publico para o pacote app conseguir criar a lista)
    public ListaIdentificadores() {
        head = new Celula();
        tail = head;
        size = 0;
    }

    /*
     * COMMIT 09
     * Título: Torna público o construtor da ListaIdentificadores
     */

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

    //retorna o id que esta na posicao i (comecando em 0)
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

    /*
     * COMMIT 07
     * Título: Adiciona acesso por posição (get) na ListaIdentificadores
     */

    //monta uma String com os ids da lista
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
     * COMMIT 08
     * Título: Adiciona toString na ListaIdentificadores
     */

    /*
     * COMMIT 18 (não aparece como código: é a REMOÇÃO do método toArray(), que existia no
     * arquivo original, depois que o Candidato deixou de usá-lo no commit 17)
     * Título: Remove toArray da ListaIdentificadores (vetores não são permitidos)
     */
}
