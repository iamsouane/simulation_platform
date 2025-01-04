package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.BanqueDeQuestionsChimie;
import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.Question;
import com.example.simulation_platform.models.Reponse;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.*;
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
    private Professeur professeur;
    private String titre;
    private String details;

    public CreerTPQuizzChimieController() {
        banqueDeQuestions = new BanqueDeQuestionsChimie();
        selectedQuestions = FXCollections.observableArrayList();
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @FXML
    public void initialize() {
        titreField.setText(titre);  // Initialiser le champ titre avec la valeur transférée

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
        if (selectedQuestions.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez ajouter au moins une question.");
            return;
        }

        // Enregistrer le TP et les questions dans la base de données
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Enregistrer le TP
            String tpQuery = "INSERT INTO tp (titre, details, matiere, typeTP, createur) VALUES (?, ?, 'CHIMIE', 'QUIZZ', ?)";
            PreparedStatement tpStatement = connection.prepareStatement(tpQuery, Statement.RETURN_GENERATED_KEYS);
            tpStatement.setString(1, titre);
            tpStatement.setString(2, details);
            tpStatement.setInt(3, professeur.getId()); // Utilisateur créateur
            tpStatement.executeUpdate();
            ResultSet tpKeys = tpStatement.getGeneratedKeys();
            tpKeys.next();
            int tpId = tpKeys.getInt(1);

            // Enregistrer le quizz
            String quizzQuery = "INSERT INTO quizz (idTP, nombreQuestions, duree) VALUES (?, ?, ?)";
            PreparedStatement quizzStatement = connection.prepareStatement(quizzQuery, Statement.RETURN_GENERATED_KEYS);
            quizzStatement.setInt(1, tpId);
            quizzStatement.setInt(2, selectedQuestions.size());
            quizzStatement.setInt(3, 30); // Durée du quizz en minutes
            quizzStatement.executeUpdate();
            ResultSet quizzKeys = quizzStatement.getGeneratedKeys();
            quizzKeys.next();
            int quizzId = quizzKeys.getInt(1);

            // Enregistrer les questions et les réponses
            String questionQuery = "INSERT INTO question (idQuizz, enonce) VALUES (?, ?)";
            String reponseQuery = "INSERT INTO reponse (idQuestion, texte, estCorrecte) VALUES (?, ?, ?)";
            for (Question question : selectedQuestions) {
                PreparedStatement questionStatement = connection.prepareStatement(questionQuery, Statement.RETURN_GENERATED_KEYS);
                questionStatement.setInt(1, quizzId);
                questionStatement.setString(2, question.getEnonce());
                questionStatement.executeUpdate();
                ResultSet questionKeys = questionStatement.getGeneratedKeys();
                questionKeys.next();
                int questionId = questionKeys.getInt(1);

                for (Reponse reponse : question.getReponses()) {
                    PreparedStatement reponseStatement = connection.prepareStatement(reponseQuery);
                    reponseStatement.setInt(1, questionId);
                    reponseStatement.setString(2, reponse.getTexte());
                    reponseStatement.setBoolean(3, reponse.isEstCorrecte());
                    reponseStatement.executeUpdate();
                }
            }

            connection.commit();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Quizz Chimie créé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la création du quizz chimie.");
        }
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