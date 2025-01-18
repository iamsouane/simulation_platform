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
                bienvenueLabel.setText("Bienvenue " + prenom + " " + nom + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer les informations du professeur.");
        }
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
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = """
                SELECT r.note, r.commentaires, t.titre, u.nom AS eleveNom, u.prenom AS elevePrenom
                FROM resultat r
                JOIN tp t ON r.tp = t.idTP
                JOIN utilisateur u ON r.eleve = u.idUtilisateur
                WHERE t.createur = ?;
                """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, professeur.getId());
            ResultSet resultSet = statement.executeQuery();

            StringBuilder resultats = new StringBuilder("Résultats des élèves :\n");

            while (resultSet.next()) {
                String eleveNom = resultSet.getString("eleveNom");
                String elevePrenom = resultSet.getString("elevePrenom");
                int note = resultSet.getInt("note");
                String commentaires = resultSet.getString("commentaires");
                String tpTitre = resultSet.getString("titre");

                resultats.append("TP: ").append(tpTitre).append("\n");
                resultats.append("Élève: ").append(elevePrenom).append(" ").append(eleveNom).append("\n");
                resultats.append("Note: ").append(note).append("/20\n");
                resultats.append("Commentaires: ").append(commentaires).append("\n\n");
            }

            showAlert(Alert.AlertType.INFORMATION, "Résultats", resultats.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la récupération des résultats.");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            // Afficher un message d'alerte pour informer de la déconnexion
            showAlert(Alert.AlertType.INFORMATION, "Déconnexion", "Vous avez été déconnecté.");

            // Charger la scène de connexion (assurez-vous que le fichier FXML existe)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/main_view.fxml"));
            Scene loginScene = new Scene(loader.load());

            // Obtenir le stage actuel et le remplacer par celui de la page de connexion
            Stage primaryStage = (Stage) bienvenueLabel.getScene().getWindow();
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
