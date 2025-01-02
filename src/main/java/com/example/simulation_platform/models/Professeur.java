package com.example.simulation_platform.models;

import java.util.ArrayList;
import java.util.List;

public class Professeur extends Utilisateur {
    private List<TP> listeTP;

    public Professeur(int idUtilisateur, String nom, String prenom, String email, String motDePasse) {
        super(idUtilisateur, nom, prenom, email, motDePasse, Role.PROFESSEUR);
        this.listeTP = new ArrayList<>();
    }

    public TP creerTP(String titre, String details, Matiere matiere, TypeTP typeTP) {
        TP tp = new TP(titre, details, matiere, typeTP, this);
        listeTP.add(tp);
        return tp;
    }

    public List<Resultat> consulterResultats(TP tp) {
        return tp.getResultats();
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