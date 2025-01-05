package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Question;
import com.example.simulation_platform.models.Reponse;
import com.example.simulation_platform.models.TP;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaireTPController {

    @FXML
    private Label titreLabel;
    @FXML
    private Label detailsLabel;
    @FXML
    private VBox questionsVBox;

    private Stage stage;
    private Eleve eleve;
    private TP tp;
    private ObservableList<Question> questions;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public void setTP(TP tp) {
        this.tp = tp;
        titreLabel.setText(tp.getTitre());
        detailsLabel.setText(tp.getDetails());
        loadQuestions();
    }

    private void loadQuestions() {
        questions = FXCollections.observableArrayList(getQuestionsFromTP(tp));
        afficherQuestions();
    }

    private List<Question> getQuestionsFromTP(TP tp) {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM question WHERE idTP = ?";
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

    private void afficherQuestions() {
        questionsVBox.getChildren().clear();
        for (Question question : questions) {
            Label questionLabel = new Label(question.getEnonce());
            ListView<Reponse> reponsesListView = new ListView<>(FXCollections.observableArrayList(question.getReponses()));
            questionsVBox.getChildren().addAll(questionLabel, reponsesListView);
        }
    }

    @FXML
    private void handleSubmit() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            for (Question question : questions) {
                List<Reponse> reponses = question.getReponses();
                for (Reponse reponse : reponses) {
                    if (reponse.isEstSelectionne()) { // Utilisation de la méthode isEstSelectionne
                        String query = "INSERT INTO reponse_eleve (idEleve, idQuestion, reponse) VALUES (?, ?, ?)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, eleve.getId());
                        statement.setInt(2, question.getId());
                        statement.setString(3, reponse.getTexte());
                        statement.executeUpdate();
                    }
                }
            }
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Vos réponses ont été enregistrées avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'enregistrement de vos réponses.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleFaireTPQuizzChimie() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_quizz_chimie.fxml");
    }

    @FXML
    private void handleFaireTPQuizzSVT() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_quizz_svt.fxml");
    }

    @FXML
    private void handleFaireTPSimulationChimie() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_simulation_chimie.fxml");
    }

    @FXML
    private void handleFaireTPSimulationSVT() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_simulation_svt.fxml");
    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            VBox vbox = loader.load();
            Scene scene = new Scene(vbox);

            if (stage != null) {
                stage.setScene(scene);
                stage.setTitle("Faire TP - Simulation Platform");
            } else {
                System.err.println("Stage is null. Cannot set scene.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}