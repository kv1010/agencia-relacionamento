package tad;

public class TesteCandidato {
    public static void main(String[] args) {
        Candidato ana = new Candidato(1, "Ana", "MG", 'F', 'M', new int[]{1, 3, 5});
        Candidato bruno = new Candidato(2, "Bruno", "MG", 'M', 'F', new int[]{3, 5, 2});
        Candidato carla = new Candidato(3, "Carla", "SP", 'F', 'M', new int[]{1, 3, 5});
        Candidato carlos = new Candidato(4, "Carlos", "MG", 'M', 'M', new int[]{1, 3, 5});
        Candidato diana = new Candidato(5, "Diana", "MG", 'F', 'I', new int[]{3, 5});

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

        // Teste com o coringa 'I'
        Candidato.setGrauMinimo(2);
        System.out.println("Bruno verifica Diana ('I'): " + bruno.verificaPotencialCompanheiro(diana)); // true

        // Casos limite
        System.out.println("Ana verifica null: " + ana.verificaPotencialCompanheiro(null)); // false
        System.out.println("Ana verifica ela mesma: " + ana.verificaPotencialCompanheiro(ana)); // false

        // Teste do setAreas
        ana.setAreas(new int[]{2});
        System.out.println("Tem interesse na área 1 após setAreas: " + ana.temInteresse(1)); // false
        System.out.println("Tem interesse na área 2 após setAreas: " + ana.temInteresse(2)); // true
    }
}