package tad;

public class Candidato {

    //atributos
    private int id;
    private String nome;
    private String siglaEstado;
    private char sexo;
    private char sexoInteresse;
    private ListaIdentificadores areas; //codigos (1 a 8) das areas
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

        //copia as areas recebidas
        if (areas != null) {
            for (int i = 0; i < areas.size(); i++) {
                marcarInteresse(areas.get(i));
            }
        }
    }

    //registra as areas de interesse na lista
    private boolean marcarInteresse(int area) {
        if (area >= 1 && area <= 8 && !this.areas.contains(area)) {
            this.areas.add(area);
            return true;
        }
        return false;
    }

    //retorna verdadeiro se o candidato possui interesse na area
    public boolean temInteresse(int area) {
        return this.areas.contains(area);
    }

    //retorna as areas
    public ListaIdentificadores getAreas() {
        return areas;
    }

    //altera areasInteresse
    public void setAreas(ListaIdentificadores novasAreas) {
        ListaIdentificadores copia = new ListaIdentificadores();
        if (novasAreas != null) {
            for (int i = 0; i < novasAreas.size(); i++) {
                copia.add(novasAreas.get(i));
            }
        }

        this.areas.clear(); //apaga as areas de interesse
        for (int i = 0; i < copia.size(); i++) {
            marcarInteresse(copia.get(i)); //insere novas
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
        int grauTotal = 0; //soma dos pesos
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
        //Mesmo estado
        if (!this.siglaEstado.equals(v.getEstado())) {
            return false;
        }
        //Orientação MÚTUA
        if (!this.atendeOrientacao(v) || !v.atendeOrientacao(this)) {
            return false;
        }
        //Grau de interesse em comum <= ao grau mínimo
        if (this.calculaGrauInteresse(v) < grauMinimo) {
            return false;
        }
        return true;
    }

    // Retorna verdadeiro se o candidato atual aceita o sexo do candidato v (considera I)
    private boolean atendeOrientacao(Candidato v) {
        return this.sexoInteresse == 'I' || this.sexoInteresse == v.getSexo();
    }

    //retorna verdadeiro se o estado do candidato corresponde a sigla fornecida
    public boolean reside(String sigla) {
        return this.siglaEstado.equalsIgnoreCase(sigla);
    }

    //limpa os companheiros que foram adicionados
    public void limpaCompanheiros() {
        this.potenciaisCompanheiros.clear();
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

    //Retorna a lista com os id dos companheiros
    public ListaIdentificadores listaCompanheiros() {
        return this.potenciaisCompanheiros;
    }

    //retorna o id
    public int getId() {
        return id;
    }

    //retorna o nome
    public String getNome() {
        return nome;
    }

    //retorna a siglaEstado
    public String getEstado() {
        return siglaEstado;
    }

    //retorna o sexo
    public char getSexo() {
        return sexo;
    }

    //retorna o sexo de interesse
    public char getSexoInteresse() {
        return sexoInteresse;
    }

    //recebe graumin
    public static int getGrauMinimo() {
        return grauMinimo;
    }
    //altera graumin
    public static void setGrauMinimo(int grauMinimo) {
        Candidato.grauMinimo = grauMinimo;
    }

    //altera nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    //altera siglaEstado
    public void setEstado(String siglaEstado) {
        if (siglaEstado != null) {
            this.siglaEstado = siglaEstado.toUpperCase();
        } else {
            this.siglaEstado = "";
        }
    }

    //altera sexo
    public void setSexo(char sexo) {
        this.sexo = Character.toUpperCase(sexo);
    }

    //altera sexoInteresse
    public void setSexoInteresse(char sexoInteresse) {
        this.sexoInteresse = Character.toUpperCase(sexoInteresse);
    }

    //texto candidato (id - nome)
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
