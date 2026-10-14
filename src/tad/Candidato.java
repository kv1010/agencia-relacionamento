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
}
