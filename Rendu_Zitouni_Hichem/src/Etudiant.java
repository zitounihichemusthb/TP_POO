public class Etudiant extends Personne implements Inscription {
    private String niveau;

    public Etudiant(String id, String nom, int age, String niveau) throws AgeInvalideException {
        super(id, nom, age);
        if (age < 18) {
            throw new AgeInvalideException("L'étudiant doit être majeur (18 ans minimum).");
        }
        this.niveau = niveau;
    }

    public String getNiveau() { return niveau; }

    @Override
    public String getType() {
        return "Etudiant";
    }

    @Override
    public String toString() {
        return super.toString() + " | Niveau: " + niveau;
    }

    // Implémentation de l'interface Inscription
    @Override
    public void inscrire(String idFormation) {
        System.out.println("-> [Info] L'étudiant " + this.nom + " demande l'inscription à la formation " + idFormation);
    }

    @Override
    public void desinscrire(String idFormation) {
        System.out.println("-> [Info] L'étudiant " + this.nom + " quitte la formation " + idFormation);
    }
}
