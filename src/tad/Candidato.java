package tad;

public class Candidato {

    //atributos
    private int id;
    private String nome;
    private String siglaEstado;
    private char sexo;
    private char sexoInteresse;
    private ListaIdentificadores areas; //codigos (1 a 8) das areas de interesse
    private ListaIdentificadores potenciaisCompanheiros; //ids dos companheiros

    private static int grauMinimo = 0; //vale para todos os candidatos

    //construtor
    public Candidato(int id, String nome, String siglaEstado, char sexo, char sexoInteresse, ListaIdentificadores areas) {
        this.id = id;
        this.nome = nome;
        if (siglaEstado != null) {
            this.siglaEstado = siglaEstado.toUpperCase();
        } else {
            this.siglaEstado = "";
        }
        this.sexo = Character.toUpperCase(sexo);
        this.sexoInteresse = Character.toUpperCase(sexoInteresse);
        this.areas = new ListaIdentificadores();
        this.potenciaisCompanheiros = new ListaIdentificadores();

        //copia as areas recebidas (so entram areas validas e sem repetir)
        if (areas != null) {
            for (int i = 0; i < areas.size(); i++) {
                marcarInteresse(areas.get(i));
            }
        }
    }

    //registrar as áreas de interesse na lista (ignora area invalida e repetida)
    private boolean marcarInteresse(int area) {
        if (area >= 1 && area <= 8 && !this.areas.contains(area)) {
            this.areas.add(area);
            return true;
        }
        return false;
    }

    //retorna verdadeiro se o candidato possui interesse na area informada
    public boolean temInteresse(int area) {
        return this.areas.contains(area);
    }

    //retorna as areas escolhidas
    public ListaIdentificadores getAreas() {
        return areas;
    }

    //altera areasInteresse
    public void setAreas(ListaIdentificadores novasAreas) {
        ListaIdentificadores copia = new ListaIdentificadores(); //copia antes de limpar, caso seja a mesma lista
        if (novasAreas != null) {
            for (int i = 0; i < novasAreas.size(); i++) {
                copia.add(novasAreas.get(i));
            }
        }

        this.areas.clear(); //apagando todas as areas de interesse
        for (int i = 0; i < copia.size(); i++) {
            marcarInteresse(copia.get(i)); //inserindo as novas
        }
    }

    //devolve o peso de uma area (1: 1, 2: 2, 3: 1, 4: 1, 5: 3, 6: 2, 7: 2, 8: 3)
    private int pesoArea(int area) {
        switch (area) {
            case 1: //ESPORTES
            case 3: //MUSICA
            case 4: //CINEMA
                return 1;
            case 2: //ARTES
            case 6: //ANIMAIS
            case 7: //GASTRONOMIA
                return 2;
            default: //TECNOLOGIA e CIENCIAS
                return 3;
        }
    }

    //calcular o grau de interesse em comum com o candidato v
    private int calculaGrauInteresse(Candidato v) {
        int grauTotal = 0;
        for (int i = 0; i < this.areas.size(); i++) {
            int area = this.areas.get(i);
            if (v.temInteresse(area)) //se ambos tem interesse na mesma area soma o peso
                grauTotal += pesoArea(area);
        }
        return grauTotal;
    }

    // Retorna verdadeiro se v é um potencial companheiro válido para este candidato
    public boolean verificaPotencialCompanheiro(Candidato v) {
        if (v == null || this.id == v.getId()) {
            return false;
        }
        if (!this.siglaEstado.equals(v.getEstado())) {
            return false;
        }
        //Orientação MÚTUA
        if (!this.atendeOrientacao(v) || !v.atendeOrientacao(this)) {
            return false;
        }
        if (this.calculaGrauInteresse(v) < grauMinimo) {
            return false;
        }
        return true;
    }

    private boolean atendeOrientacao(Candidato v) {
        return this.sexoInteresse == 'I' || this.sexoInteresse == v.getSexo();
    }

    public boolean reside(String sigla) {
        return this.siglaEstado.equalsIgnoreCase(sigla);
    }

    public void limpaCompanheiros() {
        this.potenciaisCompanheiros.clear();
    }

    public void inserePotencialCompanheiro(Candidato v) {
        if (v != null) {
            this.potenciaisCompanheiros.add(v.getId());
        }
    }

    public int numeroCompanheiros() {
        return this.potenciaisCompanheiros.size();
    }

    public ListaIdentificadores listaCompanheiros() {
        return this.potenciaisCompanheiros;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return siglaEstado;
    }

    public char getSexo() {
        return sexo;
    }

    public char getSexoInteresse() {
        return sexoInteresse;
    }

    public static int getGrauMinimo() {
        return grauMinimo;
    }

    public static void setGrauMinimo(int grauMinimo) {
        Candidato.grauMinimo = grauMinimo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstado(String siglaEstado) {
        if (siglaEstado != null) {
            this.siglaEstado = siglaEstado.toUpperCase();
        } else {
            this.siglaEstado = "";
        }
    }

    public void setSexo(char sexo) {
        this.sexo = Character.toUpperCase(sexo);
    }

    public void setSexoInteresse(char sexoInteresse) {
        this.sexoInteresse = Character.toUpperCase(sexoInteresse);
    }

    //(id - nome)
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
