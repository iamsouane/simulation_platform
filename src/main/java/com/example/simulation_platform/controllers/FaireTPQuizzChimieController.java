package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Question;
import com.example.simulation_platform.models.Reponse;
import com.example.simulation_platform.models.TP;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaireTPQuizzChimieController {

    @FXML
    private VBox questionsVBox;
    @FXML
    private Label titreLabel;

    private List<Question> questions;
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
            VBox reponsesVBox = new VBox();
            for (Reponse reponse : question.getReponses()) {
                Label reponseLabel = new Label(reponse.getTexte());
                reponsesVBox.getChildren().add(reponseLabel);
            }
            questionsVBox.getChildren().addAll(questionLabel, reponsesVBox);
        }
    }

    @FXML
    private void handleSoumettre() {
        // Logic for submitting answers
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}