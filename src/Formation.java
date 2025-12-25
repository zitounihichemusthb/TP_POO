import java.util.ArrayList;
import java.util.List;

public class Formation {
    private String idFormation;
    private String intitule;
    private int capacite;
    private List<String> etudiantsInscrits; // Liste des ID

    public Formation(String idFormation, String intitule, int capacite) {
        this.idFormation = idFormation;
        this.intitule = intitule;
        this.capacite = capacite;
        this.etudiantsInscrits = new ArrayList<>();
    }

    public String getIdFormation() { return idFormation; }
    public String getIntitule() { return intitule; }
    public int getCapacite() { return capacite; }
    public List<String> getEtudiantsInscrits() { return etudiantsInscrits; }

    public boolean estPleine() {
        return etudiantsInscrits.size() >= capacite;
    }

    public void ajouterEtudiant(String idEtudiant) throws FormationPleineException {
        if (estPleine()) {
            throw new FormationPleineException("La formation " + intitule + " est complète !");
        }
        if (!etudiantsInscrits.contains(idEtudiant)) {
            etudiantsInscrits.add(idEtudiant);
        } else {
            System.out.println("Cet étudiant est déjà inscrit dans ce cours.");
        }
    }

    public void retirerEtudiant(String idEtudiant) {
        if (etudiantsInscrits.contains(idEtudiant)) {
            etudiantsInscrits.remove(idEtudiant);
        } else {
            System.out.println("Etudiant non trouvé dans cette formation.");
        }
    }

    @Override
    public String toString() {
        return "ID: " + idFormation + " | Intitulé: " + intitule + 
               " | Places: " + etudiantsInscrits.size() + "/" + capacite;
    }
}
