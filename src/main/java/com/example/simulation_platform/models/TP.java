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

    public List<Resultat> getResultats() {
        return resultats;
    }
}