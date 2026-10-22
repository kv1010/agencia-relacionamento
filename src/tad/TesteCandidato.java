package tad;

public class TesteCandidato {

    private static ListaIdentificadores areas(int a, int b, int c) {
        ListaIdentificadores l = new ListaIdentificadores();
        l.add(a);
        l.add(b);
        l.add(c);
        return l;
    }

    private static ListaIdentificadores areas(int a, int b) {
        ListaIdentificadores l = new ListaIdentificadores();
        l.add(a);
        l.add(b);
        return l;
    }

    private static ListaIdentificadores areas(int a) {
        ListaIdentificadores l = new ListaIdentificadores();
        l.add(a);
        return l;
    }

    public static void main(String[] args) {
        Candidato ana = new Candidato(1, "Ana", "MG", 'F', 'M', areas(1, 3, 5));
        Candidato bruno = new Candidato(2, "Bruno", "MG", 'M', 'F', areas(3, 5, 2));
        Candidato carla = new Candidato(3, "Carla", "SP", 'F', 'M', areas(1, 3, 5));
        Candidato carlos = new Candidato(4, "Carlos", "MG", 'M', 'M', areas(1, 3, 5));
        Candidato diana = new Candidato(5, "Diana", "MG", 'F', 'I', areas(3, 5));

        // Teste de grau mínimo (Ana e Bruno têm grau 4 em comum)
        Candidato.setGrauMinimo(3);
        System.out.println("Ana verifica Bruno (grau min 3): " + ana.verificaPotencialCompanheiro(bruno)); // true

        Candidato.setGrauMinimo(6);
        System.out.println("Ana verifica Bruno (grau min 6): " + ana.verificaPotencialCompanheiro(bruno)); // false

        Candidato.setGrauMinimo(3);
        System.out.println("Simetria (Bruno verifica Ana): " + bruno.verificaPotencialCompanheiro(ana)); // true

        // Teste de estado diferente
        System.out.println("Ana verifica Carla (SP): " + ana.verificaPotencialCompanheiro(carla)); // false

        // Teste de orientação mútua (Carlos)
        System.out.println("Ana verifica Carlos: " + ana.verificaPotencialCompanheiro(carlos)); // false

        // Teste com o Sexo de interesse I
        Candidato.setGrauMinimo(2);
        System.out.println("Bruno verifica Diana ('I'): " + bruno.verificaPotencialCompanheiro(diana)); // true

        // Casos limite
        System.out.println("Ana verifica null: " + ana.verificaPotencialCompanheiro(null)); // false
        System.out.println("Ana verifica ela mesma: " + ana.verificaPotencialCompanheiro(ana)); // false

        // Teste de area repetida e invalida no construtor (conta uma vez so)
        ListaIdentificadores repetidas = new ListaIdentificadores();
        repetidas.add(3);
        repetidas.add(3);
        repetidas.add(9); // invalida
        Candidato erica = new Candidato(6, "Erica", "mg", 'f', 'm', repetidas);
        System.out.println("Areas da Erica: " + erica.getAreas()); // [ 3 ]
        System.out.println("Estado da Erica em maiuscula: " + erica.getEstado()); // MG

        // Teste do setAreas
        Candidato.setGrauMinimo(3);
        ana.setAreas(areas(2));
        System.out.println("Tem interesse na área 1 após setAreas: " + ana.temInteresse(1)); // false
        System.out.println("Tem interesse na área 2 após setAreas: " + ana.temInteresse(2)); // true
        ana.setAreas(areas(1, 3, 5)); // volta ao original para os proximos testes

        // Teste dos companheiros
        ana.inserePotencialCompanheiro(bruno);
        ana.inserePotencialCompanheiro(diana);
        System.out.println("Numero de companheiros da Ana: " + ana.numeroCompanheiros()); // 2
        System.out.println("Lista de companheiros da Ana: " + ana.listaCompanheiros()); // [ 2 5 ]
        ana.limpaCompanheiros();
        System.out.println("Depois de limpar: " + ana.numeroCompanheiros()); // 0

        // Teste do reside e do temInteresse
        System.out.println("Ana reside em mg: " + ana.reside("mg")); // true
        System.out.println("Ana reside em SP: " + ana.reside("SP")); // false
        System.out.println("Ana tem interesse na area 5: " + ana.temInteresse(5)); // true
        System.out.println("Ana tem interesse na area 2: " + ana.temInteresse(2)); // false

        // Teste do toString
        System.out.println("toString da Ana: " + ana); // 1 - Ana
    }
}
