package service;

import model.Employe;

import java.util.Arrays;
import java.util.Scanner;

public class GestionEmployes {
    private static Employe[] employes = new Employe[50];
    private static int count = 0;

    public static void ajouterEmploye(String nom, String poste, double salaire) {
        try {
            if (nom == null || nom.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom ne peut pas être vide.");
            }
            if (poste == null || poste.trim().isEmpty()) {
                throw new IllegalArgumentException("Le poste ne peut pas être vide.");
            }
            if (salaire <= 0) {
                throw new IllegalArgumentException("Le salaire doit être supérieur à zéro.");
            }
            Employe employe = new Employe(nom, poste, salaire);
            if (count >= employes.length) {
                System.out.println("Le tableau est plein, impossible d'ajouter plus d'employés.");
            } else {
                employes[count++] = employe;
                System.out.println("Employé ajouté avec succès: " + employe);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de l'ajout de l'employé: " + e.getMessage());
        }
    }



    public static void modifierEmploye(int id, String nouveauNom, String nouveauPoste, double nouveauSalaire) {
        try {
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (employes[i].getId() == id) {
                    employes[i].setNom(nouveauNom);
                    employes[i].setPoste(nouveauPoste);
                    employes[i].setSalaire(nouveauSalaire);
                    System.out.println("Employé modifié avec succès: " + employes[i]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Employé avec l'ID " + id + " non trouvé.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }


    public static void supprimerEmploye(int id) throws Exception {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (employes[i].getId() == id) {
                System.arraycopy(employes, i + 1, employes, i, count - i - 1);
                employes[--count] = null;
                System.out.println("Employé supprimé avec succès.");
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Employé avec l'ID " + id + " non trouvé.");
        }
    }

    public static void calculerMasseSalariale() {
        double total = 0;
        for (int i = 0; i < count; i++) {
            total += employes[i].getSalaire();
        }
        System.out.println("La masse salariale totale est de: " + total);
    }

    public static void trierEmployesParSalaire(boolean ordreCroissant) {
        Arrays.sort(employes, 0, count, (a, b)
                       -> ordreCroissant ? Double.compare(
                        a.getSalaire(), b.getSalaire()
                        ) : Double.compare(b.getSalaire(), a.getSalaire()));
        afficherEmployes();
    }

    // Menu to interact with the application
    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n--- Gestion des Employés ---");
                System.out.println("1. Ajouter un employé");
                System.out.println("2. Modifier un employé");
                System.out.println("3. Supprimer un employé");
                System.out.println("4. Afficher les employés");
                System.out.println("5. Rechercher un employé");
                System.out.println("6. Calculer la masse salariale");
                System.out.println("7. Trier les employés par salaire");
                System.out.println("0. Quitter");
                System.out.print("Entrez votre choix: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        scanner.nextLine();  // consume newline
                        System.out.print("Entrer le nom: ");
                        String nom = scanner.nextLine();
                        System.out.print("Entrer le poste: ");
                        String poste = scanner.nextLine();
                        System.out.print("Entrer le salaire: ");
                        double salaire = scanner.nextDouble();
                        ajouterEmploye(nom, poste, salaire);
                        break;
                    case 2:
                        System.out.print("Entrer l'id de l'employé à modifier: ");
                        int modId = scanner.nextInt();
                        scanner.nextLine();  // consume newline
                        System.out.print("Nouveau nom: ");
                        String nouveauNom = scanner.nextLine();
                        System.out.print("Nouveau poste: ");
                        String nouveauPoste = scanner.nextLine();
                        System.out.print("Nouveau salaire: ");
                        double nouveauSalaire = scanner.nextDouble();
                        modifierEmploye(modId, nouveauNom, nouveauPoste, nouveauSalaire);
                        break;
                    case 3:
                        System.out.print("Entrer l'id de l'employé à supprimer: ");
                        int delId = scanner.nextInt();
                        supprimerEmploye(delId);
                        break;
                    case 4:
                        afficherEmployes();
                        break;
                    case 5:
                        scanner.nextLine();  // consume newline
                        System.out.print("Entrer le nom ou le poste à rechercher: ");
                        String critere = scanner.nextLine();
                        rechercherEmploye(critere);
                        break;
                    case 6:
                        calculerMasseSalariale();
                        break;
                    case 7:
                        System.out.print("Trier par salaire (true = croissant, false = décroissant): ");
                        boolean ordre = scanner.nextBoolean();
                        trierEmployesParSalaire(ordre);
                        break;
                    case 0:
                        System.out.println("Fin du programme.");
                        scanner.close(); // close scanner to prevent resource leak
                        return;
                    default:
                        System.out.println("Choix non valide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
                scanner.nextLine(); // handle any scanner buffer issues
            }
        }
    }

    public static void afficherEmployes() {
        if (count == 0) {
            System.out.println("Aucun employé à afficher.");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.println(employes[i]);
            }
        }
    }

    public static void rechercherEmploye(String critere) {
        new Thread(() -> {
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (employes[i].getNom().equalsIgnoreCase(critere) || employes[i].getPoste().equalsIgnoreCase(critere)) {
                    System.out.println(employes[i]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Aucun employé trouvé avec ce critère: " + critere);
            }
        }).start();
    }


}
