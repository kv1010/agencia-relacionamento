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
        this.areaInteresse = new boolean[9];
        this.potenciaisCompanheiros= new  ListaIdentificadores();
    }

    //retorna verdadeiro se o estado do candidato corresponde a sigla fornecida
    public boolean reside(String sigla) {
        return this.siglaEstado.equalsIgnoreCase(sigla);
    }

    //retorna verdadeiro se o candidato possui interesse na area informada
    public boolean temInteresse(int id) {
        if (id >= 1 && id <= 8) {
            return this.areaInteresse[id];
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
}
