package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.TP;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FaireTPSimulationChimieController {

    @FXML
    private VBox questionsVBox;
    @FXML
    private Label titreLabel;

    private TP tp;
    private Eleve eleve;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public void setTP(TP tp) {
        this.tp = tp;
        titreLabel.setText(tp.getTitre());
        afficheQuestions();
    }

    private void afficheQuestions() {
        // Code pour afficher les questions de la simulation
        questionsVBox.getChildren().clear();
        // Exemple simplifié
        Label questionLabel = new Label("Question de la simulation de Chimie");
        questionsVBox.getChildren().add(questionLabel);
    }

    @FXML
    private void handleSoumettre() {
        // Code pour gérer la soumission des réponses de la simulation
        try {
            // Enregistrer les réponses de l'élève
            showAlert(Alert.AlertType.INFORMATION, "Succès", "TP Simulation Chimie soumis avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la soumission.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}