package com.example.simulation_platform.models;

import java.util.ArrayList;
import java.util.List;

public class Eleve extends Utilisateur {
    private List<Resultat> listeResultats;

    public Eleve(int idUtilisateur, String nom, String prenom, String email, String motDePasse) {
        super(idUtilisateur, nom, prenom, email, motDePasse, Role.ELEVE);
        this.listeResultats = new ArrayList<>();
    }

    public Resultat faireTP(TP tp) {
        Resultat resultat = tp.evaluer(this);
        listeResultats.add(resultat);
        return resultat;
    }

    public List<Resultat> consulterHistorique() {
        return listeResultats;
    }

    @Override
    public void sInscrire() {
        // Implementation
    }

    @Override
    public void seConnecter() {
        // Implementation
    }

    @Override
    public void seDeconnecter() {
        // Implementation
    }
}