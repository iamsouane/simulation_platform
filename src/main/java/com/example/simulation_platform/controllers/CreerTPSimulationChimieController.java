package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class CreerTPSimulationChimieController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField detailsField;
    @FXML
    private StackPane simulationContainer;
    @FXML
    private ComboBox<String> solutionComboBox;
    @FXML
    private ComboBox<String> indicateurComboBox;  // Ajout de la ComboBox pour sélectionner l'indicateur

    private Professeur professeur;  // Déclarez la variable professeur

    private Map<String, Color> solutionsMap;  // Map pour associer une solution à sa couleur
    private Map<String, Color> indicateursMap;  // Map pour associer un indicateur à une couleur

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
    private void initialize() {
        // Initialiser les solutions et leur couleur associée
        solutionsMap = new HashMap<>();
        solutionsMap.put("Eau distillée (neutre)", Color.VIOLET);
        solutionsMap.put("Vinaigre (acide)", Color.RED);
        solutionsMap.put("Eau savonneuse (basique)", Color.GREEN);
        solutionsMap.put("Jus de citron (acide)", Color.RED);
        solutionsMap.put("Bicarbonate de soude (basique)", Color.YELLOW);

        // Initialiser les indicateurs et leur couleur associée
        indicateursMap = new HashMap<>();
        indicateursMap.put("Phénolphtaléine", Color.PINK);
        indicateursMap.put("Bleu de bromothymol", Color.BLUE);
        indicateursMap.put("Jus de chou rouge", Color.VIOLET);
        indicateursMap.put("Rouge de méthyle", Color.ORANGE);

        // Ajouter une ComboBox pour sélectionner la solution
        solutionComboBox.getItems().addAll(solutionsMap.keySet());

        // Ajouter les éléments à la ComboBox pour les indicateurs
        indicateurComboBox.getItems().addAll(indicateursMap.keySet());
    }


    @FXML
    private void handleCreer() {
        String titre = titreField.getText();
        String details = detailsField.getText();

        // Vérifier que tous les champs sont remplis
        if (titre.isEmpty() || details.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier que l'utilisateur a sélectionné un indicateur et une solution
        String selectedSolution = solutionComboBox.getValue();
        String selectedIndicateur = indicateurComboBox.getValue();
        if (selectedIndicateur == null || selectedSolution == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir un indicateur et une solution.");
            return;
        }

        // Logique pour créer la simulation de chimie
        create3DSimulation(selectedSolution);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Simulation Chimie créée avec succès.");
    }



    private void create3DSimulation(String selectedSolution) {
        // Créer un bécher 1
        Cylinder becher1 = createBecher(-50);

        // Créer un bécher 2
        Cylinder becher2 = createBecher(50);

        // Appliquer la couleur de l'indicateur à Bécher 1
        String selectedIndicateur = indicateurComboBox.getValue();
        if (selectedIndicateur != null) {
            Color color = indicateursMap.get(selectedIndicateur);
            setBecherColor(becher1, color);
        }

        // Appliquer la couleur de la solution à Bécher 2
        if (selectedSolution != null) {
            Color solutionColor = solutionsMap.get(selectedSolution);
            setBecherColor(becher2, solutionColor);
        }

        // Créer les étiquettes fixes sous chaque bécher
        Text becher1Label = createFixedLabel(-30, 50, "Bécher 1");
        Text becher2Label = createFixedLabel(70, 50, "Bécher 2");

        // Créer le fond de la simulation
        Rectangle fond = new Rectangle(400, 400);
        fond.setFill(Color.CORNFLOWERBLUE);
        fond.setTranslateZ(-1);

        // Ajouter les objets à la scène 3D
        simulationContainer.getChildren().addAll(fond, becher1, becher2, becher1Label, becher2Label);

        // Déplacer le bécher 1 vers le bécher 2
        moveBecherToOther(becher1, becher2);
    }


    private Cylinder createBecher(double positionX) {
        Cylinder becher = new Cylinder(20, 100);  // Rayon et hauteur
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));  // Couleur argentée
        becher.setMaterial(material);
        becher.setTranslateX(positionX);
        return becher;
    }

    private void setBecherColor(Cylinder becher, Color color) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        becher.setMaterial(material);
    }

    private Text createFixedLabel(double positionX, double offsetY, String label) {
        Text textLabel = new Text(label);
        textLabel.setFill(Color.BLACK);
        textLabel.setTranslateX(positionX - 15);
        textLabel.setTranslateY(50 + offsetY);
        return textLabel;
    }

    private void moveBecherToOther(Cylinder becher1, Cylinder becher2) {
        // Animation pour déplacer et incliner le bécher 1
        Path path = new Path();
        path.getElements().add(new MoveTo(becher1.getTranslateX(), becher1.getTranslateY()));

        // Créer un arc de cercle pour le déplacement
        double offsetX = -50;
        path.getElements().add(new QuadCurveTo(
                (becher1.getTranslateX() + becher2.getTranslateX()) / 2,
                becher1.getTranslateY() - 150,
                becher2.getTranslateX() + offsetX,
                becher2.getTranslateY() - 90
        ));

        PathTransition pathTransition1 = new PathTransition();
        pathTransition1.setPath(path);
        pathTransition1.setNode(becher1);
        pathTransition1.setInterpolator(Interpolator.LINEAR);
        pathTransition1.setCycleCount(1);
        pathTransition1.setAutoReverse(false);
        pathTransition1.setRate(0.05);

        RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(4), becher1);
        rotateTransition1.setToAngle(90);
        rotateTransition1.setAxis(Rotate.Z_AXIS);
        rotateTransition1.setCycleCount(1);
        rotateTransition1.setAutoReverse(false);

        pathTransition1.setOnFinished(event -> {
            rotateTransition1.play();
        });

        rotateTransition1.setOnFinished(event -> {
            // Changer la couleur du bécher 2 en fonction de l'indicateur
            String selectedIndicateur = indicateurComboBox.getValue();
            if (selectedIndicateur != null) {
                Color color = indicateursMap.get(selectedIndicateur);
                setBecherColor(becher2, color);
            }

            // Rendre le bécher 1 transparent
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.rgb(192, 192, 192, 0.2)); // Couleur argentée avec transparence
            becher1.setMaterial(material);
        });

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
