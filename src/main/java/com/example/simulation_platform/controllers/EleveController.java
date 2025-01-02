package com.example.simulation_platform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class EleveController {

    @FXML
    private void handleFaireTP() {
        showAlert(Alert.AlertType.INFORMATION, "Faire TP", "Fonctionnalité pour faire un TP.");
    }

    @FXML
    private void handleConsulterHistorique() {
        showAlert(Alert.AlertType.INFORMATION, "Consulter Historique", "Fonctionnalité pour consulter l'historique.");
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