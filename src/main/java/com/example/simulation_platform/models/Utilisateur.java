package com.example.simulation_platform.models;

public abstract class Utilisateur {
    protected int idUtilisateur;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String motDePasse;
    protected Role role;

    public Utilisateur(int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public abstract void sInscrire();
    public abstract void seConnecter();
    public abstract void seDeconnecter();
}