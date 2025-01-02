package com.example.simulation_platform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ProfesseurController {

    @FXML
    private void handleCreerTP() {
        showAlert(Alert.AlertType.INFORMATION, "Créer TP", "Fonctionnalité de création de TP.");
    }

    @FXML
    private void handleConsulterResultats() {
        showAlert(Alert.AlertType.INFORMATION, "Consulter Résultats", "Fonctionnalité de consultation des résultats.");
    }

    @FXML
    private void handleLogout() {
        showAlert(Alert.AlertType.INFORMATION, "Déconnexion", "Vous avez été déconnecté.");
        // Logique pour retourner à la page de connexion
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}