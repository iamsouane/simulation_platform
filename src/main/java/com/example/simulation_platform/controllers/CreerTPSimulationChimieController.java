package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
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
        // Créer un bécher 1
        Cylinder becher1 = createBecher(-50);

        // Créer un bécher 2
        Cylinder becher2 = createBecher(50);

        // Créer les étiquettes fixes sous chaque bécher
        Text becher1Label = createFixedLabel(-30, 50, "Bécher 1");
        Text becher2Label = createFixedLabel(70, 50, "Bécher 2");

        // Créer le fond de la simulation (représenté par un rectangle)
        Rectangle fond = new Rectangle(400, 400);
        fond.setFill(Color.CORNFLOWERBLUE);
        fond.setTranslateZ(-1); // Assurez-vous que le fond est derrière les béchers

        // Ajouter les objets à la scène 3D
        simulationContainer.getChildren().addAll(fond, becher1, becher2, becher1Label, becher2Label);

        // Déplacer le bécher 1 vers le bécher 2 pour simuler l'action de verser en arc de cercle
        moveBecherToOther(becher1, becher2);
    }

    private Text createFixedLabel(double positionX, double offsetY, String label) {
        Text textLabel = new Text(label);
        textLabel.setFill(Color.BLACK);
        textLabel.setTranslateX(positionX - 15);  // Centrer légèrement par rapport au bécher
        textLabel.setTranslateY(50 + offsetY);  // Position sous le bécher (en fonction de la hauteur du bécher)
        return textLabel;
    }

    private Cylinder createBecher(double positionX) {
        Cylinder becher = new Cylinder(20, 100);  // Rayon et hauteur
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));  // Couleur argentée avec transparence
        becher.setMaterial(material);
        becher.setTranslateX(positionX);
        return becher;
    }

    private void moveBecherToOther(Cylinder becher1, Cylinder becher2) {
        // Animation du bécher 1 : déplace le bécher en arc de cercle pour se retrouver à côté de becher2
        Path path = new Path();
        path.getElements().add(new MoveTo(becher1.getTranslateX(), becher1.getTranslateY()));

        // Crée un arc de cercle qui finit à côté de becher2
        double offsetX = -50;  // Décalage horizontal pour être à côté de becher2
        path.getElements().add(new QuadCurveTo(
                (becher1.getTranslateX() + becher2.getTranslateX()) / 2, // Point de contrôle pour l'arc
                becher1.getTranslateY() - 150,                          // Hauteur maximale de l'arc
                becher2.getTranslateX() + offsetX,                      // Position finale : à côté de becher2
                becher2.getTranslateY() - 90                          // Position finale : au niveau de becher2
        ));

        PathTransition pathTransition1 = new PathTransition();
        pathTransition1.setPath(path);
        pathTransition1.setNode(becher1);
        pathTransition1.setInterpolator(Interpolator.LINEAR);
        pathTransition1.setCycleCount(1);
        pathTransition1.setAutoReverse(false);
        pathTransition1.setRate(0.05);  // Réduction de la vitesse

        // Animation pour incliner le bécher 1 pour simuler le versement
        RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(4), becher1);  // Inclinaison lente
        rotateTransition1.setToAngle(90);  // Inclinaison plus marquée pour simuler un versement
        rotateTransition1.setAxis(Rotate.Z_AXIS);  // Rotation autour de l'axe Z (inclinaison latérale)
        rotateTransition1.setCycleCount(1);
        rotateTransition1.setAutoReverse(false);

        // Synchroniser les animations (inclinaison après le déplacement)
        pathTransition1.setOnFinished(event -> rotateTransition1.play());

        // Démarrer les animations
        pathTransition1.play();
    }




    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
