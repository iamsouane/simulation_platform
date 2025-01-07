package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Path;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.PerspectiveCamera;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class CreerTPSimulationChimieController {

    @FXML
    private TextField champTitre;

    @FXML
    private TextArea champDetails;

    @FXML
    private ComboBox<String> comboSolution;

    @FXML
    private Label labelResultat;

    @FXML
    private StackPane stackPaneSimulation;

    private Professeur professeur;
    private Stage stage;

    private static final Map<String, String> solutionPHMap = new HashMap<>();
    private static final Map<String, Color> solutionColorMap = new HashMap<>();

    static {
        solutionPHMap.put("Eau distillée (neutre)", "Violet (pH 7 - Neutre)");
        solutionPHMap.put("Vinaigre (acide)", "Rouge (pH 2 - Acide)");
        solutionPHMap.put("Eau savonneuse (basique)", "Vert (pH 9 - Basique)");
        solutionPHMap.put("Jus de citron (acide)", "Rouge (pH 2 - Acide)");
        solutionPHMap.put("Bicarbonate de soude (basique)", "Jaune (pH 8 - Basique)");

        solutionColorMap.put("Eau distillée (neutre)", Color.VIOLET);
        solutionColorMap.put("Vinaigre (acide)", Color.RED);
        solutionColorMap.put("Eau savonneuse (basique)", Color.GREEN);
        solutionColorMap.put("Jus de citron (acide)", Color.RED);
        solutionColorMap.put("Bicarbonate de soude (basique)", Color.YELLOW);
    }

    private Group root3D;
    private SubScene sousSceneSimulation;
    private Cylinder becher;
    private Cylinder solution;
    private Sphere indicateur;

    public void initialize() {
        // Initialiser la scène 3D
        root3D = new Group();
        sousSceneSimulation = new SubScene(root3D, 600, 400, true, SceneAntialiasing.BALANCED);
        sousSceneSimulation.setFill(Color.LIGHTBLUE);
        sousSceneSimulation.setCamera(createCamera());

        // Ajouter SubScene à StackPane
        stackPaneSimulation.getChildren().add(sousSceneSimulation);

        // Créer le bécher
        becher = createBecher();
        root3D.getChildren().add(becher);

        // Créer la solution
        solution = createSolution();
        root3D.getChildren().add(solution);

        // Créer l'indicateur
        indicateur = createIndicateur();
        root3D.getChildren().add(indicateur);
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitre(String titre) {
        champTitre.setText(titre); // Initialiser le champ titre avec la valeur transférée
    }

    public void setDetails(String details) {
        champDetails.setText(details); // Initialiser le champ détails avec la valeur transférée
    }

    @FXML
    private void handleCreer() {
        String titre = champTitre.getText();
        String details = champDetails.getText();

        if (titre.isEmpty() || details.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Logique pour créer la simulation de chimie
        // Vous pouvez ajouter ici des actions supplémentaires si nécessaire

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Simulation Chimie créée avec succès.");
    }

    @FXML
    private void handleAjouterIndicateur() {
        String solutionType = comboSolution.getValue();
        if (solutionType == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir une solution.");
            return;
        }

        String result = solutionPHMap.get(solutionType);
        labelResultat.setText(result);

        Color solutionColor = solutionColorMap.get(solutionType);

        // Animation de l'indicateur tombant dans la solution
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(5));
        transition.setNode(indicateur);
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));  // Commence au centre
        path.getElements().add(new LineTo(0, 200));  // Déplace l'indicateur vers le bas
        transition.setPath(path);
        transition.setCycleCount(1);
        transition.setOnFinished(e -> {
            // Changer la couleur de la solution après l'animation
            PhongMaterial material = (PhongMaterial) solution.getMaterial();
            material.setDiffuseColor(solutionColor);
            material.setSpecularColor(solutionColor.darker());

            // Modifier la couleur de l'indicateur pour refléter la solution
            PhongMaterial indicatorMaterial = (PhongMaterial) indicateur.getMaterial();
            indicatorMaterial.setDiffuseColor(solutionColor);
        });
        transition.play();
    }

    @FXML
    private void handleRetour() {
        stage.close(); // Fermer la fenêtre actuelle
    }

    private PerspectiveCamera createCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -50)
        );
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setFieldOfView(35);
        return camera;
    }

    private Cylinder createBecher() {
        Cylinder cylinder = new Cylinder(50, 100);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);
        cylinder.setMaterial(material);
        cylinder.setTranslateX(300);
        cylinder.setTranslateY(200);
        cylinder.setTranslateZ(0);
        return cylinder;
    }

    private Cylinder createSolution() {
        Cylinder cylinder = new Cylinder(45, 90);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTBLUE);
        cylinder.setMaterial(material);
        cylinder.setTranslateX(300);
        cylinder.setTranslateY(200);
        cylinder.setTranslateZ(0);
        return cylinder;
    }

    private Sphere createIndicateur() {
        Sphere sphere = new Sphere(10);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.ORANGE);
        sphere.setMaterial(material);
        sphere.setTranslateX(300);
        sphere.setTranslateY(0);
        sphere.setTranslateZ(0);
        return sphere;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}