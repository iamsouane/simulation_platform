package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Question;
import com.example.simulation_platform.models.ReponseEleve;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class FaireTPQuizzSVTController {

    @FXML
    private VBox questionsVBox;
    @FXML
    private Label titreLabel;

    private List<Question> questions;
    private List<ReponseEleve> reponsesEleve;
    private int idEleve = 1; // Remplacez par l'identifiant de l'élève actuel

    public FaireTPQuizzSVTController() {
        questions = new ArrayList<>();
        reponsesEleve = new ArrayList<>();
        // Initialize with some questions (this should come from the database or a service)
    }

    @FXML
    public void initialize() {
        afficheQuestions();
    }

    public void setTitre(String titre) {
        titreLabel.setText(titre);
    }

    private void afficheQuestions() {
        questionsVBox.getChildren().clear();
        for (Question question : questions) {
            Label questionLabel = new Label(question.getEnonce());
            ToggleGroup toggleGroup = new ToggleGroup();
            VBox reponsesVBox = new VBox();
            for (int i = 0; i < question.getReponses().size(); i++) {
                RadioButton radioButton = new RadioButton(question.getReponses().get(i).getTexte());
                radioButton.setToggleGroup(toggleGroup);
                radioButton.setUserData(question.getReponses().get(i).getTexte());
                reponsesVBox.getChildren().add(radioButton);
            }
            questionsVBox.getChildren().addAll(questionLabel, reponsesVBox);
        }
    }

    @FXML
    private void handleSoumettre() {
        reponsesEleve.clear();
        for (int i = 0; i < questionsVBox.getChildren().size(); i += 2) {
            Label questionLabel = (Label) questionsVBox.getChildren().get(i);
            VBox reponsesVBox = (VBox) questionsVBox.getChildren().get(i + 1);
            ToggleGroup toggleGroup = null;

            // Rechercher le ToggleGroup parmi les enfants de reponsesVBox
            for (javafx.scene.Node node : reponsesVBox.getChildren()) {
                if (node instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) node;
                    toggleGroup = radioButton.getToggleGroup();
                    break;
                }
            }

            if (toggleGroup != null) {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                if (selectedRadioButton != null) {
                    String reponse = selectedRadioButton.getUserData().toString();
                    int idQuestion = i / 2; // Assurez-vous que l'idQuestion est correctement récupéré
                    reponsesEleve.add(new ReponseEleve(idEleve, idQuestion, reponse));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez répondre à toutes les questions.");
                    return;
                }
            }
        }

        // Afficher un message de succès après la soumission des réponses
        showAlert(Alert.AlertType.INFORMATION, "Succès", "TP Quizz SVT soumis avec succès.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}