package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class CreerTPSimulationChimieController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField detailsField;
    @FXML
    private StackPane simulationContainer;

    private Professeur professeur;  // Déclarez la variable professeur

    // Méthode pour définir le professeur
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setTitre(String titre) {
        titreField.setText(titre);
    }

    public void setDetails(String details) {
        detailsField.setText(details);
    }

    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String details = detailsField.getText();

        if (titre.isEmpty() || details.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Logique pour créer la simulation de chimie
        create3DSimulation();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Simulation Chimie créée avec succès.");
    }

    private void create3DSimulation() {
        // Créer un bécher 1 (cylindre) avec une couleur transparente
        Cylinder becher1 = new Cylinder(20, 100);  // rayon et hauteur
        PhongMaterial material1 = new PhongMaterial();
        material1.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));  // Couleur argentée avec transparence (alpha = 0.2)
        becher1.setMaterial(material1);
        becher1.setTranslateX(-50);

        // Créer un bécher 2 (cylindre) avec une couleur transparente
        Cylinder becher2 = new Cylinder(20, 100);  // rayon et hauteur
        PhongMaterial material2 = new PhongMaterial();
        material2.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));  // Couleur argentée avec transparence (alpha = 0.2)
        becher2.setMaterial(material2);
        becher2.setTranslateX(50);

        // Créer le fond de la simulation (représenté par un rectangle)
        Rectangle fond = new Rectangle(400, 400);
        fond.setFill(Color.LIGHTGRAY);
        fond.setTranslateZ(-1); // Assurez-vous que le fond est derrière les béchers

        // Ajouter les objets à la scène 3D
        simulationContainer.getChildren().addAll(fond, becher1, becher2);
    }




    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
