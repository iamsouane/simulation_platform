package com.example.simulation_platform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreerTPQuizzSVTController {

    @FXML
    private TextField titreField;
    @FXML
    private TextArea questionsField;

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String questions = questionsField.getText();

        if (titre.isEmpty() || questions.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Logique pour créer le quizz SVT
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Quizz SVT créé avec succès.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}