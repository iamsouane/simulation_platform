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
}