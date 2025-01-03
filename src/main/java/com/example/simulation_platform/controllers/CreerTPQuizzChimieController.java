package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.BanqueDeQuestionsChimie;
import com.example.simulation_platform.models.Question;
import com.example.simulation_platform.models.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CreerTPQuizzChimieController {

    @FXML
    private TextField titreField;
    @FXML
    private ComboBox<String> questionsComboBox;
    @FXML
    private ListView<String> optionsListView;
    @FXML
    private VBox questionsVBox;

    private BanqueDeQuestionsChimie banqueDeQuestions;
    private ObservableList<Question> selectedQuestions;

    public CreerTPQuizzChimieController() {
        banqueDeQuestions = new BanqueDeQuestionsChimie();
        selectedQuestions = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        for (Question question : banqueDeQuestions.getQuestions()) {
            questionsComboBox.getItems().add(question.getEnonce());
        }

        questionsComboBox.setOnAction(e -> {
            String selectedQuestion = questionsComboBox.getValue();
            optionsListView.getItems().clear();
            for (Question question : banqueDeQuestions.getQuestions()) {
                if (question.getEnonce().equals(selectedQuestion)) {
                    for (Reponse reponse : question.getReponses()) {
                        optionsListView.getItems().add(reponse.getTexte() + (reponse.isEstCorrecte() ? " (Correct)" : ""));
                    }
                    break;
                }
            }
        });
    }

    @FXML
    private void handleAjouterQuestion() {
        String selectedQuestionEnonce = questionsComboBox.getValue();
        if (selectedQuestionEnonce == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une question.");
            return;
        }

        for (Question question : banqueDeQuestions.getQuestions()) {
            if (question.getEnonce().equals(selectedQuestionEnonce)) {
                selectedQuestions.add(question);
                afficherQuestions();
                break;
            }
        }
    }

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();

        if (titre.isEmpty() || selectedQuestions.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs et ajouter au moins une question.");
            return;
        }

        // Logique pour créer le quizz chimie sans enregistrement dans la base de données
        // Vous pouvez ajouter ici des actions supplémentaires si nécessaire

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Quizz Chimie créé avec succès.");
    }

    private void afficherQuestions() {
        questionsVBox.getChildren().clear();
        for (Question question : selectedQuestions) {
            Label questionLabel = new Label(question.getEnonce());
            ListView<String> reponsesListView = new ListView<>();
            for (Reponse reponse : question.getReponses()) {
                reponsesListView.getItems().add(reponse.getTexte() + (reponse.isEstCorrecte() ? " (Correct)" : ""));
            }
            questionsVBox.getChildren().addAll(questionLabel, reponsesListView);
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