import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class InterfaceGraphique extends JFrame {
    private Map<String, Personne> personnes;
    private Map<String, Formation> formations;
    private JTextArea zoneAffichage;

    public InterfaceGraphique(Map<String, Personne> personnes, Map<String, Formation> formations) {
        this.personnes = personnes;
        this.formations = formations;

        // Configuration de la fenêtre
        setTitle("Gestion Centre Formation (Hichem Zitouni)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- HAUT : Titre ---
        JLabel titre = new JLabel("Application Gestion Formation - M1 BigData", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        // --- CENTRE : Zone de texte ---
        zoneAffichage = new JTextArea();
        zoneAffichage.setEditable(false);
        zoneAffichage.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(zoneAffichage), BorderLayout.CENTER);

        // --- BAS : Boutons ---
        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setLayout(new GridLayout(1, 4, 10, 10)); // 1 ligne, 4 boutons

        JButton btnAfficher = new JButton("Afficher Tout");
        JButton btnAddEtudiant = new JButton("Ajouter Etudiant");
        JButton btnAddFormation = new JButton("Ajouter Formation");
        JButton btnQuitter = new JButton("Quitter");

        panneauBoutons.add(btnAfficher);
        panneauBoutons.add(btnAddEtudiant);
        panneauBoutons.add(btnAddFormation);
        panneauBoutons.add(btnQuitter);
        panneauBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panneauBoutons, BorderLayout.SOUTH);

        // --- ACTIONS ---
        
        // Action 1 : Afficher
        btnAfficher.addActionListener(e -> rafraichirAffichage());

        // Action 2 : Ajouter Etudiant (Popup simple)
        btnAddEtudiant.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID Etudiant :");
            if(id == null || id.isEmpty()) return;
            String nom = JOptionPane.showInputDialog("Nom :");
            String ageStr = JOptionPane.showInputDialog("Age :");
            String niveau = JOptionPane.showInputDialog("Niveau :");

            try {
                int age = Integer.parseInt(ageStr);
                Etudiant et = new Etudiant(id, nom, age, niveau);
                personnes.put(id, et);
                rafraichirAffichage();
                JOptionPane.showMessageDialog(this, "Etudiant ajouté !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        // Action 3 : Ajouter Formation
        btnAddFormation.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID Formation :");
            if(id == null || id.isEmpty()) return;
            String intitule = JOptionPane.showInputDialog("Intitulé :");
            String capStr = JOptionPane.showInputDialog("Capacité :");
            
            try {
                int cap = Integer.parseInt(capStr);
                Formation f = new Formation(id, intitule, cap);
                formations.put(id, f);
                rafraichirAffichage();
                JOptionPane.showMessageDialog(this, "Formation ajoutée !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de saisie.");
            }
        });

        // Action 4 : Quitter
        btnQuitter.addActionListener(e -> System.exit(0));

        
        setLocationRelativeTo(null); // Centrer
        setVisible(true);
    }

    private void rafraichirAffichage() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTE DES ETUDIANTS ===\n");
        for (Personne p : personnes.values()) {
            if (p instanceof Etudiant) sb.append(p).append("\n");
        }
        sb.append("\n=== LISTE DES FORMATEURS ===\n");
        for (Personne p : personnes.values()) {
            if (p instanceof Formateur) sb.append(p).append("\n");
        }
        sb.append("\n=== LISTE DES FORMATIONS ===\n");
        for (Formation f : formations.values()) {
            sb.append(f).append("\n");
        }
        zoneAffichage.setText(sb.toString());
    }
}
