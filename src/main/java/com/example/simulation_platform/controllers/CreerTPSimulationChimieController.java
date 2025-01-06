package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreerTPSimulationChimieController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField detailsField;

    private Professeur professeur;
    private String titre;
    private String details;

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
        titreField.setText(titre); // Initialiser le champ titre avec la valeur transférée
    }

    public void setDetails(String details) {
        this.details = details;
        detailsField.setText(details); // Initialiser le champ détails avec la valeur transférée
    }

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String details = detailsField.getText();

        if (titre.isEmpty() || details.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Logique pour créer la simulation de chimie
        // Vous pouvez ajouter ici des actions supplémentaires si nécessaire

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Simulation Chimie créée avec succès.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}