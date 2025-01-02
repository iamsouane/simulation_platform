package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Matiere;
import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.TP;
import com.example.simulation_platform.models.TypeTP;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String details = detailsField.getText();
        Matiere matiere = Matiere.valueOf(matiereComboBox.getValue().toUpperCase());
        TypeTP typeTP = TypeTP.valueOf(typeTPComboBox.getValue().toUpperCase());

        if (titre.isEmpty() || details.isEmpty() || matiere == null || typeTP == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        TP tp = professeur.creerTP(titre, details, matiere, typeTP);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "TP créé avec succès.");
        // Logique supplémentaire pour enregistrer le TP dans la base de données peut être ajoutée ici
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}