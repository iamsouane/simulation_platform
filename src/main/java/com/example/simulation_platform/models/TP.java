package com.example.simulation_platform.models;

import java.util.ArrayList;
import java.util.List;

public class TP {
    private static int idCounter = 1;
    private int idTP;
    private String titre;
    private String details;
    private Matiere matiere;
    private TypeTP typeTP;
    private Professeur createur;
    private List<Resultat> resultats;

    public TP(String titre, String details, Matiere matiere, TypeTP typeTP, Professeur createur) {
        this.idTP = idCounter++;
        this.titre = titre;
        this.details = details;
        this.matiere = matiere;
        this.typeTP = typeTP;
        this.createur = createur;
        this.resultats = new ArrayList<>();
    }

    // Getters
    public int getIdTP() {
        return idTP;
    }

    public String getTitre() {
        return titre;
    }

    public String getDetails() {
        return details;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public TypeTP getTypeTP() {
        return typeTP;
    }

    public Professeur getCreateur() {
        return createur;
    }

    public List<Resultat> getResultats() {
        return resultats;
    }

    public void demarrerTP() {
        // Implementation
    }

    public void afficherDetails() {
        // Implementation
    }

    public Resultat evaluer(Eleve eleve) {
        Resultat resultat = new Resultat(eleve, this);
        resultats.add(resultat);
        return resultat;
    }

    // Redéfinition de la méthode toString pour afficher les TP correctement dans la ListView
    @Override
    public String toString() {
        return matiere + " (" + typeTP + ") - Créé par: " + createur.getNom().toUpperCase() + " " + createur.getPrenom().toUpperCase();
    }
}
