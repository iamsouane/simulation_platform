package com.example.simulation_platform.models;

public class Reponse {
    private int id;
    private String texte;
    private boolean estCorrecte;

    // Constructeur
    public Reponse(int id, String texte, boolean estCorrecte) {
        this.id = id;
        this.texte = texte;
        this.estCorrecte = estCorrecte;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public boolean isEstCorrecte() {
        return estCorrecte;
    }

    public void setEstCorrecte(boolean estCorrecte) {
        this.estCorrecte = estCorrecte;
    }
}