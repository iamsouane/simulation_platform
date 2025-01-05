package com.example.simulation_platform.models;

public class Resultat {
    private static int idCounter = 1;
    private int idResultat;
    private int note;
    private String commentaires;
    private Eleve eleve;
    private TP tp;

    public Resultat(Eleve eleve, TP tp) {
        this.idResultat = idCounter++;
        this.eleve = eleve;
        this.tp = tp;
        this.note = 0; // Default value, to be set after evaluation
        this.commentaires = "";
    }

    // Getters and Setters
    public int getIdResultat() {
        return idResultat;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public TP getTp() {
        return tp;
    }
}