package model;

public class Employe {
    private static int nextId = 1;
    private int id;
    private String nom;
    private String poste;
    private double salaire;

    // Constructor with all attributes except ID
    public Employe(String nom, String poste, double salaire) {
        if (nom == null || nom.trim().isEmpty() || poste == null || poste.trim().isEmpty() || salaire <= 0) {
            throw new IllegalArgumentException("Les champs nom, poste et salaire doivent être valides.");
        }
        this.id = nextId++;
        this.nom = nom;
        this.poste = poste;
        this.salaire = salaire;
    }
    public Employe() {
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPoste() {
        return poste;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public void setPoste(String poste) {
        if (poste == null || poste.trim().isEmpty()) {
            throw new IllegalArgumentException("Le poste ne peut pas être vide.");
        }
        this.poste = poste;
    }

    public void setSalaire(double salaire) {
        if (salaire <= 0) {
            throw new IllegalArgumentException("Le salaire doit être supérieur à zéro.");
        }
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", poste='" + poste + '\'' +
                ", salaire=" + salaire +
                '}';
    }

    public static int compareParSalaire(Employe a, Employe b) {
        return Double.compare(a.getSalaire(), b.getSalaire());
    }

}

