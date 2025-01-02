package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class EleveController {

    private Eleve eleve;
    private Stage stage;

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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