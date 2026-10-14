package tad;

public class Candidato {
    
    //atributos
    private int id;
    private String nome;
    private String siglaEstado;
    private char sexo;
    private char sexoInteresse;
    private boolean[] areaInteresse; //vetor de 1 a 8 das areas
    private ListaIdentificadores potenciaisCompanheiros; //lista encadeada

    //construtor
    public Candidato(int id, String nome, String siglaEstado, char sexo, char sexoInteresse, boolean[] areaInteresse, ListaIdentificadores potenciaisCompanheiros) {
        this.id = id;
        this.nome = nome;
        this.siglaEstado = siglaEstado;
        this.sexo = sexo;
        this.sexoInteresse = sexoInteresse;
        this.areaInteresse = new boolean[8];
        this.potenciaisCompanheiros= new  ListaIdentificadores();
    }

    //calcular o grau de interesse em comum com o candidato v
    private int calculaGrauInteresse(Candidato v) {
        int[] pesos = {1, 2, 1, 1, 3, 2, 2, 3}; //pesos das areas
        int grauTotal = 0; //local para guardar a soma para os pesos
        for (int i = 0; i < pesos.length; i++) {
            if (this.temInteresse(i) && v.temInteresse(i)) { //se ambos tem interessa na mesma area soma o peso
                grauTotal += pesos[i];
            }
        }
        return grauTotal;
    }

    //retorna verdadeiro se o estado do candidato corresponde a sigla fornecida
    public boolean reside(String sigla) {
        return this.siglaEstado.equalsIgnoreCase(sigla);
    }

    //retorna verdadeiro se o candidato possui interesse na area informada
    public boolean temInteresse(int id) {
        if (id >= 1 && id <= 8) {
            return this.areaInteresse[(id - 1)];
        }
        return false;
    }

    //registar as áreas de interesse no vetor
    public boolean marcarInteresse(int id) {
        if (id >= 1 && id <=8) {
            return this.areaInteresse[id] = true;
        }
        return false;
    }

    //retorna o id
    public int getId() {
        return id;
    }

    //Adiciona o identificador do candidato v na lista de potenciais companheiros
    public void inserePotencialCompanheiro(Candidato v) {
        if (v != null) {
            this.potenciaisCompanheiros.add(v.getId());
        }
    }

    //Retorna quantos companheiros estão na lista
    public int numeroCompanheiros() {
        return this.potenciaisCompanheiros.size();
    }

    //Retorna o vetor (int[]) com os IDs dos companheiros
    public int[] listaCompanheiros() {
        return this.potenciaisCompanheiros.toArray();
    }
    
}
