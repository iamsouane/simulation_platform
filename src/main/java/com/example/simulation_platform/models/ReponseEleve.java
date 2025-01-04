package com.example.simulation_platform.models;

public class ReponseEleve {
    private int idEleve;
    private int idQuestion;
    private String reponse;

    // Constructeur
    public ReponseEleve(int idEleve, int idQuestion, String reponse) {
        this.idEleve = idEleve;
        this.idQuestion = idQuestion;
        this.reponse = reponse;
    }

    // Getters et setters
    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}