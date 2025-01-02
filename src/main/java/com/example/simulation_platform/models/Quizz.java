package com.example.simulation_platform.models;

public class Quizz extends TP {
    private int nombreQuestions;
    private int duree;

    public Quizz(String titre, String details, Matiere matiere, Professeur createur, int nombreQuestions, int duree) {
        super(titre, details, matiere, TypeTP.QUIZZ, createur);
        this.nombreQuestions = nombreQuestions;
        this.duree = duree;
    }

    public void repondreQuestions() {
        // Implementation
    }
}