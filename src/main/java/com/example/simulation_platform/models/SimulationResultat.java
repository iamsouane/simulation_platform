package com.example.simulation_platform.models;

public class SimulationResultat {
    private int id;
    private String titre;
    private String details;
    private String solution;
    private String indicateur;
    private String couleurFinale;
    private String description;
    private Professeur createur;

    // Constructeur
    public SimulationResultat(int id, String titre, String details, String solution, String indicateur,
                              String couleurFinale, String description, Professeur createur) {
        this.id = id;
        this.titre = titre;
        this.details = details;
        this.solution = solution;
        this.indicateur = indicateur;
        this.couleurFinale = couleurFinale;
        this.description = description;
        this.createur = createur;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getIndicateur() {
        return indicateur;
    }

    public void setIndicateur(String indicateur) {
        this.indicateur = indicateur;
    }

    public String getCouleurFinale() {
        return couleurFinale;
    }

    public void setCouleurFinale(String couleurFinale) {
        this.couleurFinale = couleurFinale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Professeur getCreateur() {
        return createur;
    }

    public void setCreateur(Professeur createur) {
        this.createur = createur;
    }

    @Override
    public String toString() {
        return "SimulationResultat [id=" + id + ", titre=" + titre + ", details=" + details + ", solution=" + solution
                + ", indicateur=" + indicateur + ", couleurFinale=" + couleurFinale + ", description=" + description
                + ", createur=" + createur + "]";
    }
}