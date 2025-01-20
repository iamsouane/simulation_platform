package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.*;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaireTPQuizzSVTController {

    @FXML
    private VBox questionsVBox;

    private List<Question> questions;
    private TP tp;
    private Eleve eleve;
    private Lambda lambda;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public void setLambda(Lambda lambda) {
        this.lambda = lambda;
    }

    public void setTP(TP tp) {
        this.tp = tp;
        questions = getQuestionsFromTP(tp);
        afficheQuestions();
    }

    private List<Question> getQuestionsFromTP(TP tp) {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM question WHERE idQuizz = (SELECT idQuizz FROM quizz WHERE idTP = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tp.getIdTP());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idQuestion = resultSet.getInt("idQuestion");
                String enonce = resultSet.getString("enonce");
                List<Reponse> reponses = getReponsesFromQuestion(idQuestion, connection);
                Question question = new Question(idQuestion, enonce, reponses);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors du chargement des questions.");
        }
        return questions;
    }

    private List<Reponse> getReponsesFromQuestion(int idQuestion, Connection connection) throws SQLException {
        List<Reponse> reponses = new ArrayList<>();
        String query = "SELECT * FROM reponse WHERE idQuestion = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idQuestion);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int idReponse = resultSet.getInt("idReponse");
            String texte = resultSet.getString("texte");
            boolean estCorrecte = resultSet.getBoolean("estCorrecte");
            Reponse reponse = new Reponse(idReponse, texte, estCorrecte);
            reponses.add(reponse);
        }
        return reponses;
    }

    private void afficheQuestions() {
        questionsVBox.getChildren().clear();
        for (Question question : questions) {
            Label questionLabel = new Label(question.getEnonce());
            ToggleGroup toggleGroup = new ToggleGroup();
            VBox reponsesVBox = new VBox();
            for (Reponse reponse : question.getReponses()) {
                RadioButton radioButton = new RadioButton(reponse.getTexte());
                radioButton.setToggleGroup(toggleGroup);
                radioButton.setUserData(reponse);
                reponsesVBox.getChildren().add(radioButton);
            }
            questionsVBox.getChildren().addAll(questionLabel, reponsesVBox);
        }
    }

    @FXML
    private void handleSoumettre() {
        List<Reponse> reponsesEleve = new ArrayList<>();
        int correctAnswers = 0;
        for (int i = 0; i < questionsVBox.getChildren().size(); i += 2) {
            Label questionLabel = (Label) questionsVBox.getChildren().get(i);
            VBox reponsesVBox = (VBox) questionsVBox.getChildren().get(i + 1);
            ToggleGroup toggleGroup = null;

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
                    Reponse reponse = (Reponse) selectedRadioButton.getUserData();
                    reponsesEleve.add(reponse);
                    if (reponse.isEstCorrecte()) {
                        correctAnswers++;
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez répondre à toutes les questions.");
                    return;
                }
            }
        }

        int totalQuestions = questions.size();
        double pointsPerQuestion = 20.0 / totalQuestions;
        int score = (int) (correctAnswers * pointsPerQuestion); // Note sur 20

        String commentaires;
        if (score >= 16) {
            commentaires = "Très bien";
        } else if (score >= 14) {
            commentaires = "Bien";
        } else if (score >= 12) {
            commentaires = "Assez bien";
        } else if (score >= 10) {
            commentaires = "Passable";
        } else {
            commentaires = "Insuffisant";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO resultat (eleve, tp, note, commentaires) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, eleve.getId());
            statement.setInt(2, tp.getIdTP());
            statement.setInt(3, score);
            statement.setString(4, commentaires);
            statement.executeUpdate();

            for (Reponse reponse : reponsesEleve) {
                String queryReponse = "INSERT INTO reponse_eleve (idEleve, idQuestion, reponse) VALUES (?, ?, ?)";
                PreparedStatement statementReponse = connection.prepareStatement(queryReponse);
                statementReponse.setInt(1, eleve.getId());
                statementReponse.setInt(2, getIdQuestionByReponse(connection, reponse.getId()));
                statementReponse.setString(3, reponse.getTexte());
                statementReponse.executeUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Succès", "TP Quizz SVT soumis avec succès. Votre score est : " + score + "/20\nCommentaires : " + commentaires);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la soumission.");
        }
    }

    private int getIdQuestionByReponse(Connection connection, int idReponse) throws SQLException {
        String query = "SELECT idQuestion FROM reponse WHERE idReponse = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idReponse);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("idQuestion");
        } else {
            throw new SQLException("Question not found for response id: " + idReponse);
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