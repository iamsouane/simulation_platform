package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

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
        // Créer un bécher 1 (cylindre)
        Cylinder becher1 = new Cylinder(20, 100);  // rayon et hauteur
        PhongMaterial material1 = new PhongMaterial();
        material1.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));  // Couleur argentée avec transparence
        becher1.setMaterial(material1);
        becher1.setTranslateX(-50);

        // Créer un bécher 2 (cylindre)
        Cylinder becher2 = new Cylinder(20, 100);  // rayon et hauteur
        PhongMaterial material2 = new PhongMaterial();
        material2.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));  // Couleur argentée avec transparence
        becher2.setMaterial(material2);
        becher2.setTranslateX(50);

        // Créer le fond de la simulation (représenté par un rectangle)
        Rectangle fond = new Rectangle(400, 400);
        fond.setFill(Color.LIGHTGRAY);
        fond.setTranslateZ(-1); // Assurez-vous que le fond est derrière les béchers

        // Ajouter les objets à la scène 3D
        simulationContainer.getChildren().addAll(fond, becher1, becher2);

        // Déplacer le bécher 1 vers le bécher 2 pour simuler l'action de verser en arc de cercle
        moveBecherToOther(becher1, becher2);
    }


    private void moveBecherToOther(Cylinder becher1, Cylinder becher2) {
        // Animation du bécher 1 : déplace le bécher en arc de cercle sans pénétrer dans le bécher 2
        Path path = new Path();
        path.getElements().add(new MoveTo(becher1.getTranslateX(), becher1.getTranslateY()));

        // Crée un arc de cercle avec la courbe qui s'arrête au-dessus du bécher 2
        path.getElements().add(new QuadCurveTo(becher2.getTranslateX(), becher1.getTranslateY() - 100, becher2.getTranslateX(), becher2.getTranslateY() - 50));  // Au-dessus du bécher 2

        PathTransition pathTransition1 = new PathTransition();
        pathTransition1.setPath(path);
        pathTransition1.setNode(becher1);
        pathTransition1.setInterpolator(Interpolator.LINEAR);
        pathTransition1.setCycleCount(1);
        pathTransition1.setAutoReverse(false);
        pathTransition1.setRate(0.05);

        // Animation pour incliner le bécher 1 légèrement (sans le faire entrer dans le bécher 2)
        RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(1), becher1);
        rotateTransition1.setToAngle(45);  // Inclinaison plus douce pour simuler l'action de verser
        rotateTransition1.setAxis(Rotate.X_AXIS);
        rotateTransition1.setCycleCount(1);
        rotateTransition1.setAutoReverse(false);

        // Démarrer les animations
        pathTransition1.play();
        rotateTransition1.play();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
