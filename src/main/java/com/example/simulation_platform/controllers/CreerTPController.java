package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreerTPController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField detailsField;
    @FXML
    private ComboBox<String> matiereComboBox;
    @FXML
    private ComboBox<String> typeTPComboBox;

    private Professeur professeur;
    private Stage stage;

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String details = detailsField.getText();
        String matiere = matiereComboBox.getValue();
        String typeTP = typeTPComboBox.getValue();

        if (titre.isEmpty() || details.isEmpty() || matiere == null || typeTP == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            String fxmlPath = getFxmlPath(matiere, typeTP);
            if (fxmlPath == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Chemin FXML non trouvé pour la combinaison sélectionnée.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            if (typeTP.equals("Quizz")) {
                if (matiere.equals("Chimie")) {
                    CreerTPQuizzChimieController controller = loader.getController();
                } else if (matiere.equals("SVT")) {
                    CreerTPQuizzSVTController controller = loader.getController();
                }
            } else if (typeTP.equals("Simulation")) {
                if (matiere.equals("Chimie")) {
                    CreerTPSimulationChimieController controller = loader.getController();
                } else if (matiere.equals("SVT")) {
                    CreerTPSimulationSVTController controller = loader.getController();
                }
            }

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la vue.");
        }
    }

    private String getFxmlPath(String matiere, String typeTP) {
        if (typeTP.equals("Quizz")) {
            if (matiere.equals("Chimie")) {
                return "/com/example/simulation_platform/views/creer_tp_quizz_chimie.fxml";
            } else if (matiere.equals("SVT")) {
                return "/com/example/simulation_platform/views/creer_tp_quizz_svt.fxml";
            }
        } else if (typeTP.equals("Simulation")) {
            if (matiere.equals("Chimie")) {
                return "/com/example/simulation_platform/views/creer_tp_simulation_chimie.fxml";
            } else if (matiere.equals("SVT")) {
                return "/com/example/simulation_platform/views/creer_tp_simulation_svt.fxml";
            }
        }
        return null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}