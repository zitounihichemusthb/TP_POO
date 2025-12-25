public abstract class Personne {
    protected String id;
    protected String nom;
    protected int age;

    public Personne(String id, String nom, int age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public int getAge() { return age; }

    public abstract String getType();

    @Override
    public String toString() {
        return "Type: " + getType() + " | ID: " + id + " | Nom: " + nom + " | Age: " + age;
    }
}
