package com.example.simulation_platform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FaireTPSimulationChimieController {

    @FXML
    private VBox simulationVBox;
    @FXML
    private Label titreLabel;

    public FaireTPSimulationChimieController() {
        // Initialize with some simulation steps (this should come from the database or a service)
    }

    @FXML
    public void initialize() {
        afficheSimulation();
    }

    public void setTitre(String titre) {
        titreLabel.setText(titre);
    }

    private void afficheSimulation() {
        // Logic to display simulation steps
        // For example, simulationVBox.getChildren().add(new Label("Step 1: ..."));
    }

    @FXML
    private void handleSoumettre() {
        // Logic to submit the simulation results (e.g., save them to the database)

        showAlert(Alert.AlertType.INFORMATION, "Succès", "TP Simulation Chimie soumis avec succès.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}