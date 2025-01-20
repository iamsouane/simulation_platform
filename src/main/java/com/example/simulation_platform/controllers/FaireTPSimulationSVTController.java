package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.TP;
import javafx.animation.FillTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FaireTPSimulationSVTController {

    @FXML
    private StackPane stackPanePlantes;
    @FXML
    private Slider sliderTemperature;
    @FXML
    private Label labelTemperature;
    @FXML
    private Label descriptionTemperature;
    @FXML
    private Slider sliderHumidite;
    @FXML
    private Label labelHumidite;
    @FXML
    private Label descriptionHumidite;
    @FXML
    private Slider sliderLumiere;
    @FXML
    private Label labelLumiere;
    @FXML
    private Label descriptionLumiere;
    @FXML
    private Button btnEnregistrer;

    private Professeur professeur;
    private Stage stage;
    private Eleve eleve;
    private TP tp;

    private String titre;   // Stocke le titre du TP
    private String details; // Stocke les détails du TP

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public void setTP(TP tp) {
        this.tp = tp;
        if (tp != null) {
            this.titre = tp.getTitre();
            this.details = tp.getDetails();
        }
    }

    @FXML
    public void enregistrerTP() {
        if (titre == null || titre.isEmpty() || details == null || details.isEmpty()) {
            showAlert("Erreur", "Le titre et les détails du TP ne peuvent pas être vides.", Alert.AlertType.ERROR);
            return;
        }

        String matiere = "SVT";
        String typeTP = "SIMULATION";
        int createurId = eleve.getId();

        boolean success = enregistrerTPDansBaseDeDonnees(titre, details, matiere, typeTP, createurId);

        if (success) {
            showAlert("Succès", "Le TP a été enregistré avec succès.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Une erreur est survenue lors de l'enregistrement du TP.", Alert.AlertType.ERROR);
        }
    }

    private boolean enregistrerTPDansBaseDeDonnees(String titre, String details, String matiere, String typeTP, int createurId) {
        String url = "jdbc:mysql://localhost:3306/simulation_platform";
        String user = "root";
        String password = "1234"; // Remplace par ton mot de passe

        String sql = "INSERT INTO tp (titre, details, matiere, typeTP, createur) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, titre);
            stmt.setString(2, details);
            stmt.setString(3, matiere);
            stmt.setString(4, typeTP);
            stmt.setInt(5, createurId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
