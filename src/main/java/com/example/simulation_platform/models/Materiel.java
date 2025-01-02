package com.example.simulation_platform.models;

public class Materiel {
    private int idMateriel;
    private String nom;
    private String caracteristiques;

    public Materiel(int idMateriel, String nom, String caracteristiques) {
        this.idMateriel = idMateriel;
        this.nom = nom;
        this.caracteristiques = caracteristiques;
    }

    public void afficherDetails() {
        // Implementation
    }
}