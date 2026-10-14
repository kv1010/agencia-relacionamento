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

    private static int grauMinimo = 0;

    //construtor
    public Candidato(int id, String nome, String siglaEstado, char sexo, char sexoInteresse, int[] areas) {
        this.id = id;
        this.nome = nome;
        if (siglaEstado != null) {
            this.siglaEstado = siglaEstado.toUpperCase();
        } else {
            this.siglaEstado = "";
        }
        this.sexo = Character.toUpperCase(sexo);
        this.sexoInteresse = Character.toUpperCase(sexoInteresse);
        this.areaInteresse = new boolean[8];
        this.potenciaisCompanheiros= new ListaIdentificadores();

        if (areas != null) {
            for (int i = 0; i < areas.length; i++) {
                marcarInteresse(areas[i]);
            }
        }
    }

    //calcular o grau de interesse em comum com o candidato v
    private int calculaGrauInteresse(Candidato v) {
        int[] pesos = {1, 2, 1, 1, 3, 2, 2, 3}; //pesos das areas
        int grauTotal = 0; //local para guardar a soma para os pesos
        for (int i = 0; i < pesos.length; i++) {
            int idArea = (i + 1);
            if (this.temInteresse(idArea) && v.temInteresse(idArea)) //se ambos tem interessa na mesma area soma o peso
                grauTotal += pesos[i];
        }
        return grauTotal;
    }

    // Retorna verdadeiro se v é um potencial companheiro válido para este candidato
    public boolean verificaPotencialCompanheiro(Candidato v) {
        //não é null e é diferente de this
        if (v == null || this.id == v.getId()) {
            return false;
        }

        //Mesmo estado
        if (!this.siglaEstado.equals(v.getEstado())) {
            return false;
        }

        //Orientação MÚTUA (this atende v E v atende esta)
        if (!this.atendeOrientacao(v) || !v.atendeOrientacao(this)) {
            return false;
        }

        // d) Grau de interesse em comum maior ou igual ao grau mínimo estático (cálculo pesado por último)
        if (this.calculaGrauInteresse(v) < grauMinimo) {
            return false;
        }

        return true;
    }

    // Retorna verdadeiro se o candidato atual aceita o sexo do candidato v (considerando 'I' como coringa)
    public boolean atendeOrientacao(Candidato v) {
        return this.sexoInteresse == 'I' || this.sexoInteresse == v.getSexo();
    }

    //retorna verdadeiro se o estado do candidato corresponde a sigla fornecida
    public boolean reside(String sigla) {
        return this.siglaEstado.equalsIgnoreCase(sigla);
    }

    //retorna verdadeiro se o candidato possui interesse na area informada
    public boolean temInteresse(int area) {
        if (area >= 1 && area <= 8) {
            return this.areaInteresse[(area - 1)];
        }
        return false;
    }

    //limpa os companheiros que foram adicionados
    public void limpaCompanheiros() {
        this.potenciaisCompanheiros.clear();
    }

    //registrar as áreas de interesse no vetor
    private boolean marcarInteresse(int area) {
        if (area >= 1 && area <=8) {
            this.areaInteresse[(area - 1)] = true;
            return true;
        }
        return false;
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

    //retorna as areas escolhidas
    public int[] getAreas() {
        int contAreas = 0; //conta a qtd de areas escolhidas
        for (int i = 0; i < areaInteresse.length; i++) {
            if (areaInteresse[i]) {
                contAreas ++;
            }
        }
        
        int[] areas = new int[contAreas]; //vetor para as areas escolhidas
        int idArea = 0; //contador para o vetor
        for (int i = 0; i < areaInteresse.length; i++) {
            if (areaInteresse[i]) {
                areas[idArea] = (i+1);
                idArea++; //aumenta o indice somente quando recebe um elemento
            }
        }
        return areas;
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

    //altera areasInteresse
    public void setAreas(int[] areas) {
        for (int i = 0; i < areaInteresse.length; i++) { 
            this.areaInteresse[i] = false; //apagando todas as areas de interesse
        }
        
        if (areas != null) {
            for (int i = 0; i < areas.length; i++) {
                marcarInteresse(areas[i]); //inserindo as novas
            }
        }
    }
}
