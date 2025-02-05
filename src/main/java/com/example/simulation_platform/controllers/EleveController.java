package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Matiere;
import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.TP;
import com.example.simulation_platform.models.TypeTP;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EleveController {

    private Stage stage;
    private Eleve eleve;

    @FXML
    private VBox mainVBox;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<TP> tpListView;

    public void setStage(Stage stage) {
        this.stage = stage;

        // Maximiser la fenêtre
        stage.setMaximized(true);

        // Ajuster la taille pour correspondre aux dimensions de l'écran
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Bienvenue " + eleve.getPrenom() + " " + eleve.getNom() + "!");
        }
        loadTPs();
    }

    private void loadTPs() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = """
                SELECT tp.*, utilisateur.idUtilisateur AS profId, utilisateur.nom AS profNom, 
                       utilisateur.prenom AS profPrenom, utilisateur.email AS profEmail, 
                       utilisateur.motDePasse AS profMotDePasse
                FROM tp
                JOIN utilisateur ON tp.createur = utilisateur.idUtilisateur
                """;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String details = resultSet.getString("details");
                Matiere matiere = Matiere.valueOf(resultSet.getString("matiere"));
                TypeTP typeTP = TypeTP.valueOf(resultSet.getString("typeTP"));
                Professeur createur = new Professeur(
                        resultSet.getInt("profId"),
                        resultSet.getString("profNom"),
                        resultSet.getString("profPrenom"),
                        resultSet.getString("profEmail"),
                        resultSet.getString("profMotDePasse")
                );

                TP tp = new TP(titre, details, matiere, typeTP, createur);
                tpListView.getItems().add(tp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors du chargement des TP.");
        }
    }

    @FXML
    private void handleFaireTPQuizz() {
        TP selectedTP = tpListView.getSelectionModel().getSelectedItem();
        if (selectedTP != null) {
            // Vérification si le TP est de type QUIZZ
            if (selectedTP.getTypeTP() != TypeTP.QUIZZ) {
                showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un TP de type Quizz.");
                return;  // On arrête l'exécution si le TP n'est pas de type Quizz
            }

            // Déterminer quel FXML charger en fonction de la matière
            String fxmlFile = determineFXMLFile(selectedTP);
            if (fxmlFile == null) {
                showAlert(Alert.AlertType.WARNING, "Avertissement", "Type de TP incompatible.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                VBox faireTpView = loader.load();
                Scene scene = new Scene(faireTpView);

                if (fxmlFile.endsWith("faire_tp_quizz_chimie.fxml")) {
                    FaireTPQuizzChimieController controller = loader.getController();
                    controller.setStage(stage);
                    controller.setEleve(eleve);
                    controller.setTP(selectedTP);
                } else if (fxmlFile.endsWith("faire_tp_quizz_svt.fxml")) {
                    FaireTPQuizzSVTController controller = loader.getController();
                    controller.setStage(stage);
                    controller.setEleve(eleve);
                    controller.setTP(selectedTP);
                }

                stage.setScene(scene);
                stage.setTitle("Faire TP Quizz - " + selectedTP.getTitre());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors du chargement du TP.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un TP.");
        }
    }

    @FXML
    private void handleFaireTPSimulation() {
        TP selectedTP = tpListView.getSelectionModel().getSelectedItem();
        if (selectedTP != null) {
            // Vérification si le TP est de type SIMULATION
            if (selectedTP.getTypeTP() != TypeTP.SIMULATION) {
                showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un TP de type Simulation.");
                return;  // On arrête l'exécution si le TP n'est pas de type Simulation
            }

            // Déterminer quel FXML charger en fonction de la matière
            String fxmlFile = determineFXMLFile(selectedTP);
            if (fxmlFile == null) {
                showAlert(Alert.AlertType.WARNING, "Avertissement", "Type de TP incompatible.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                VBox faireTpView = loader.load();
                Scene scene = new Scene(faireTpView);

                if (fxmlFile.endsWith("faire_tp_simulation_chimie.fxml")) {
                    FaireTPSimulationChimieController controller = loader.getController();
                    controller.setStage(stage);
                    controller.setEleve(eleve);
                    controller.setTP(selectedTP);
                } else if (fxmlFile.endsWith("faire_tp_simulation_svt.fxml")) {
                    FaireTPSimulationSVTController controller = loader.getController();
                    controller.setStage(stage);
                    controller.setEleve(eleve);
                    controller.setTP(selectedTP);
                }

                stage.setScene(scene);
                stage.setTitle("Faire TP Simulation - " + selectedTP.getTitre());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors du chargement du TP.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un TP.");
        }
    }

    private String determineFXMLFile(TP tp) {
        if (tp.getTypeTP() == TypeTP.QUIZZ) {
            if (tp.getMatiere() == Matiere.CHIMIE) {
                return "/com/example/simulation_platform/views/faire_tp_quizz_chimie.fxml";
            } else if (tp.getMatiere() == Matiere.SVT) {
                return "/com/example/simulation_platform/views/faire_tp_quizz_svt.fxml";
            }
        } else if (tp.getTypeTP() == TypeTP.SIMULATION) {
            if (tp.getMatiere() == Matiere.CHIMIE) {
                return "/com/example/simulation_platform/views/faire_tp_simulation_chimie.fxml";
            } else if (tp.getMatiere() == Matiere.SVT) {
                return "/com/example/simulation_platform/views/faire_tp_simulation_svt.fxml";
            }
        }
        return null;
    }

    @FXML
    private void handleConsulterResultat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/consulter_ses_resultats.fxml"));
            VBox consulterResultatsView = loader.load();

            ConsulterSesResultatsController controller = loader.getController();
            controller.setEleve(eleve);
            controller.setStage(stage);  // Ajout de cette ligne

            Scene scene = new Scene(consulterResultatsView);
            stage.setScene(scene);
            stage.setTitle("Consulter vos résultats");

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors du chargement des résultats.");
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
            Stage primaryStage = (Stage) welcomeLabel.getScene().getWindow();
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