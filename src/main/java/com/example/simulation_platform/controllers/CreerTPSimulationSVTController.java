package com.example.simulation_platform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreerTPSimulationSVTController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField detailsField;

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String details = detailsField.getText();

        if (titre.isEmpty() || details.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Logique pour créer la simulation de SVT
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Simulation SVT créée avec succès.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}