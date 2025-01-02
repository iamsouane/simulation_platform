package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Lambda;
import com.example.simulation_platform.models.Utilisateur;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainViewController {

    private Stage stage;

    @FXML
    private VBox loginForm;
    @FXML
    private VBox signupForm;

    @FXML
    private TextField loginEmailField;
    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private TextField signupNomField;
    @FXML
    private TextField signupPrenomField;
    @FXML
    private TextField signupEmailField;
    @FXML
    private PasswordField signupPasswordField;
    @FXML
    private ComboBox<String> signupRoleComboBox;

    // Définir la méthode setStage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void showLoginForm() {
        loginForm.setVisible(true);
        signupForm.setVisible(false);
    }

    @FXML
    private void showSignupForm() {
        loginForm.setVisible(false);
        signupForm.setVisible(true);
    }

    @FXML
    private void handleLogin() {
        String email = loginEmailField.getText();
        String password = loginPasswordField.getText();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateur WHERE email = ? AND motDePasse = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                Utilisateur utilisateur;
                if (role.equals("PROFESSEUR")) {
                    utilisateur = new Professeur(
                            resultSet.getInt("idUtilisateur"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("email"),
                            resultSet.getString("motDePasse")
                    );
                } else if (role.equals("ELEVE")) {
                    utilisateur = new Eleve(
                            resultSet.getInt("idUtilisateur"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("email"),
                            resultSet.getString("motDePasse")
                    );
                } else {
                    utilisateur = new Lambda(
                            resultSet.getInt("idUtilisateur"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("email"),
                            resultSet.getString("motDePasse")
                    );
                }
                // Rediriger vers la page d'accueil de l'utilisateur
                // ...
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Email ou mot de passe incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignup() {
        String nom = signupNomField.getText();
        String prenom = signupPrenomField.getText();
        String email = signupEmailField.getText();
        String password = signupPasswordField.getText();
        String role = signupRoleComboBox.getValue().toUpperCase();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, role);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Inscription réussie", "Votre compte a été créé avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "Une erreur est survenue lors de la création de votre compte.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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