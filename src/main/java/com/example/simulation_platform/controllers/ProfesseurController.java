package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesseurController {

    private Professeur professeur;
    private Stage stage;


    @FXML
    private Label bienvenueLabel; // Label pour afficher le message de bienvenue

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
        // Récupérer les informations du professeur et les afficher
        loadProfesseurInfo();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadProfesseurInfo() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Requête pour récupérer les informations du professeur
            String query = "SELECT nom, prenom FROM utilisateur WHERE idUtilisateur = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, professeur.getId());  // ID du professeur
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Récupération du nom et prénom
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");

                // Mise à jour du message de bienvenue
                bienvenueLabel.setText("Bienvenue " + prenom.toUpperCase() + " " + nom.toUpperCase() + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer les informations du professeur.");
        }
    }

    @FXML
    private void handleCreerTP() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/creer_tp.fxml"));
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/consulter_resultats_eleve.fxml"));
            Scene scene = new Scene(loader.load());

            // Récupérer le contrôleur et lui passer le professeur et le stage
            ConsulterResultatsEleveController controller = loader.getController();
            controller.setProfesseur(professeur);
            controller.setStage(stage);
            controller.loadResultats(); // Charger les résultats

            // Changer la scène pour afficher les résultats
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la vue des résultats.");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            // Afficher un message d'alerte pour informer de la déconnexion
            showAlert(Alert.AlertType.INFORMATION, "Déconnexion", "Vous avez été déconnecté.");

            // Charger la scène de connexion (MainView)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/main_view.fxml"));
            Scene loginScene = new Scene(loader.load());

            // Récupérer le contrôleur et lui passer le stage
            MainViewController mainController = loader.getController();
            Stage primaryStage = (Stage) bienvenueLabel.getScene().getWindow();
            mainController.setStage(primaryStage);

            // Remettre la scène principale
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page de connexion.");
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
