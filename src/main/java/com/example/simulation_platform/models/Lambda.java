package com.example.simulation_platform.models;

public class Lambda extends Utilisateur {

    public Lambda(int idUtilisateur, String nom, String prenom, String email, String motDePasse) {
        super(idUtilisateur, nom, prenom, email, motDePasse, Role.LAMBDA);
    }

    public void testerTP(TP tp) {
        // Implementation for testing TP
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