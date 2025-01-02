package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfesseurController {

    private Professeur professeur;
    private Stage stage;

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCreerTP() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/creer_tp_view.fxml"));
            Scene scene = new Scene(loader.load());

            CreerTPController controller = loader.getController();
            controller.setProfesseur(professeur);
            controller.setStage(stage); // Passer le stage au contrôleur

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la vue de création de TP.");
        }
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