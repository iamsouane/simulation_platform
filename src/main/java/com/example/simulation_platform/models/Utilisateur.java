package com.example.simulation_platform.models;

public abstract class Utilisateur {
    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Role role;

    public Utilisateur(int idUtilisateur, String nom, String prenom, String email, String motDePasse, Role role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public abstract void sInscrire();

    public abstract void seConnecter();

    public abstract void seDeconnecter();
}