package com.example.simulation_platform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class FaireTPController {

    @FXML
    private VBox mainVBox;

    @FXML
    private void handleFaireQuizzChimie() {
        // Logique pour charger la vue faire_tp_quizz_chimie.fxml
        // Vous pouvez utiliser une méthode pour changer la scène ou charger dynamiquement le contenu
    }

    @FXML
    private void handleFaireQuizzSVT() {
        // Logique pour charger la vue faire_tp_quizz_svt.fxml
    }

    @FXML
    private void handleFaireSimulationChimie() {
        // Logique pour charger la vue faire_tp_simulation_chimie.fxml
    }

    @FXML
    private void handleFaireSimulationSVT() {
        // Logique pour charger la vue faire_tp_simulation_svt.fxml
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}