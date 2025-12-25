import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class CentreFormation {
    private static Map<String, Personne> personnes = new HashMap<>();
    private static Map<String, Formation> formations = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            personnes.put("E1", new Etudiant("E1", "Hichem", 23, "M1 BigData"));
            personnes.put("F1", new Formateur("F1", "Dr. Smith", 45, "IA"));
            formations.put("JAVA", new Formation("JAVA", "POO Avancee", 30));
        } catch (Exception e) {}

        System.out.println("==========================================");
        System.out.println("   GESTION CENTRE DE FORMATION (USTHB)");
        System.out.println("==========================================");
        System.out.println("1. Mode CONSOLE");
        System.out.println("2. Mode GRAPHIQUE (Bonus)");
        System.out.print(">>> Votre choix : ");
        
        String choixMode = scanner.nextLine();

        if (choixMode.equals("2")) {
            SwingUtilities.invokeLater(() -> {
                new InterfaceGraphique(personnes, formations);
            });
        } else {
            lancerMenuConsole(); 
        }
    }

    private static void lancerMenuConsole() {
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = lireEntier();

            switch (choix) {
                case 1:
                    gererEtudiants();
                    break;
                case 2:
                    gererFormateurs();
                    break;
                case 3:
                    gererFormations();
                    break;
                case 4:
                    inscrireEtudiant();
                    break;
                case 5:
                    afficherTout();
                    break;
                case 0:
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Gestion des ETUDIANTS");
        System.out.println("2. Gestion des FORMATEURS");
        System.out.println("3. Gestion des FORMATIONS");
        System.out.println("4. INSCRIRE un etudiant");
        System.out.println("5. AFFICHER tout");
        System.out.println("0. QUITTER");
        System.out.print(">>> Votre choix : ");
    }

    private static void gererEtudiants() {
        System.out.println("\n--- ETUDIANTS ---");
        System.out.println("1. Ajouter | 2. Supprimer");
        int subChoix = lireEntier();
        
        if (subChoix == 1) {
            try {
                System.out.print("ID : "); String id = scanner.nextLine();
                verifierPersonneExistePas(id);
                System.out.print("Nom : "); String nom = scanner.nextLine();
                System.out.print("Age : "); int age = lireEntier();
                System.out.print("Niveau : "); String niveau = scanner.nextLine();

                Etudiant e = new Etudiant(id, nom, age, niveau);
                personnes.put(id, e);
                System.out.println("Succes.");
            } catch (PersonneDejaExistanteException | AgeInvalideException e) {
                System.out.println("ERREUR : " + e.getMessage());
            }
        } else if (subChoix == 2) {
            System.out.print("ID a supprimer : ");
            String id = scanner.nextLine();
            if (personnes.remove(id) != null) System.out.println("Supprime.");
            else System.out.println("Introuvable.");
        }
    }

    private static void gererFormateurs() {
        System.out.println("\n--- FORMATEURS ---");
        System.out.println("1. Ajouter | 2. Supprimer");
        int subChoix = lireEntier();

        if (subChoix == 1) {
            try {
                System.out.print("ID : "); String id = scanner.nextLine();
                verifierPersonneExistePas(id);
                System.out.print("Nom : "); String nom = scanner.nextLine();
                System.out.print("Age : "); int age = lireEntier();
                System.out.print("Specialite : "); String spe = scanner.nextLine();

                Formateur f = new Formateur(id, nom, age, spe);
                personnes.put(id, f);
                System.out.println("Succes.");
            } catch (PersonneDejaExistanteException e) {
                System.out.println("ERREUR : " + e.getMessage());
            }
        } else if (subChoix == 2) {
            System.out.print("ID a supprimer : ");
            String id = scanner.nextLine();
            if (personnes.remove(id) != null) System.out.println("Supprime.");
            else System.out.println("Introuvable.");
        }
    }

    private static void gererFormations() {
        System.out.println("\n--- FORMATIONS ---");
        System.out.println("1. Ajouter | 2. Supprimer");
        int subChoix = lireEntier();

        if (subChoix == 1) {
            System.out.print("ID Formation : "); String id = scanner.nextLine();
            if (formations.containsKey(id)) {
                System.out.println("Erreur : ID deja utilise.");
                return;
            }
            System.out.print("Intitule : "); String intitule = scanner.nextLine();
            System.out.print("Capacite max : "); int cap = lireEntier();

            Formation f = new Formation(id, intitule, cap);
            formations.put(id, f);
            System.out.println("Succes.");

        } else if (subChoix == 2) {
            System.out.print("ID a supprimer : ");
            String id = scanner.nextLine();
            if (formations.remove(id) != null) System.out.println("Supprimee.");
            else System.out.println("Introuvable.");
        }
    }

    private static void inscrireEtudiant() {
        try {
            System.out.print("ID Etudiant : "); String idEtud = scanner.nextLine();
            Personne p = personnes.get(idEtud);

            if (p == null) {
                System.out.println("Personne introuvable.");
                return;
            }
            if (!(p instanceof Etudiant)) {
                System.out.println("Ce n'est pas un etudiant !");
                return;
            }

            System.out.print("ID Formation : "); String idForm = scanner.nextLine();
            Formation f = formations.get(idForm);
            if (f == null) {
                throw new FormationIntrouvableException("Formation inexistante.");
            }

            ((Etudiant) p).inscrire(idForm);
            f.ajouterEtudiant(idEtud);
            System.out.println("Succes.");

        } catch (FormationIntrouvableException | FormationPleineException e) {
            System.out.println("ERREUR : " + e.getMessage());
        }
    }

    private static void afficherTout() {
        System.out.println("\n--- LISTING ---");
        
        System.out.println(">> ETUDIANTS :");
        for (Personne p : personnes.values()) {
            if (p instanceof Etudiant) System.out.println(" - " + p);
        }

        System.out.println("\n>> FORMATEURS :");
        for (Personne p : personnes.values()) {
            if (p instanceof Formateur) System.out.println(" - " + p);
        }

        System.out.println("\n>> FORMATIONS :");
        for (Formation f : formations.values()) {
            System.out.println(" - " + f);
            System.out.println("   Inscrits : " + f.getEtudiantsInscrits());
        }
    }

    private static void verifierPersonneExistePas(String id) throws PersonneDejaExistanteException {
        if (personnes.containsKey(id)) {
            throw new PersonneDejaExistanteException("Cet ID est deja pris !");
        }
    }

    private static int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
