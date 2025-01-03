package com.example.simulation_platform.models;

import java.util.List;

public class Question {
    private int id;
    private String enonce;
    private List<Reponse> reponses;

    // Constructeur
    public Question(int id, String enonce, List<Reponse> reponses) {
        this.id = id;
        this.enonce = enonce;
        this.reponses = reponses;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }
}