package com.example.simulation_platform.models;

import java.util.List;

public class Simulation extends TP {
    private List<Materiel> listeMateriels;

    public Simulation(String titre, String details, Matiere matiere, Professeur createur, List<Materiel> listeMateriels) {
        super(titre, details, matiere, TypeTP.SIMULATION, createur);
        this.listeMateriels = listeMateriels;
    }

    public void executerSimulation() {
        // Implementation
    }
}