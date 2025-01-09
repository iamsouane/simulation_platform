package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
    private ComboBox<String> comboIndicateur;

    @FXML
    private Label labelResultat;

    @FXML
    private StackPane stackPaneSimulation;

    private Professeur professeur;
    private Stage stage;

    private static final Map<String, String> solutionPHMap = new HashMap<>();
    private static final Map<String, Color> solutionColorMap = new HashMap<>();
    private static final Map<String, Color> indicateurColorMap = new HashMap<>();

    static {
        // Solutions avec leurs propriétés de pH et couleurs
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

        // Indicateurs avec leurs couleurs
        indicateurColorMap.put("Phénolphtaléine", Color.PINK);
        indicateurColorMap.put("Bleu de bromothymol", Color.BLUE);
        indicateurColorMap.put("Jus de chou rouge", Color.PURPLE);
        indicateurColorMap.put("Rouge de méthyle", Color.ORANGE);
    }

    private Group root3D;
    private SubScene sousSceneSimulation;
    private Cylinder becherIndicateur;
    private Cylinder becherSolution;
    private Cylinder indicateur;
    private Cylinder solution;

    public void initialize() {
        // Initialiser la scène 3D
        root3D = new Group();
        sousSceneSimulation = new SubScene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);
        sousSceneSimulation.setFill(Color.LIGHTBLUE);
        sousSceneSimulation.setCamera(createCamera());

        stackPaneSimulation.getChildren().add(sousSceneSimulation);

        // Initialiser les objets 3D
        becherIndicateur = createBecher(-100, 0, 200);
        root3D.getChildren().add(becherIndicateur);

        becherSolution = createBecher(100, 0, 200);
        root3D.getChildren().add(becherSolution);

        indicateur = createSolutionCylinder();
        indicateur.setTranslateX(-100);
        indicateur.setTranslateY(50); // Positionner à l'intérieur du bécher
        indicateur.setVisible(false);
        root3D.getChildren().add(indicateur);

        solution = createSolutionCylinder();
        solution.setTranslateX(100);
        solution.setTranslateY(50); // Positionner à l'intérieur du bécher
        solution.setVisible(false);
        root3D.getChildren().add(solution);

        // Initialiser les ComboBox
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        // Ajouter les éléments dans les ComboBox une seule fois
        if (comboSolution.getItems().isEmpty()) {
            comboSolution.getItems().addAll(solutionPHMap.keySet());
        }

        if (comboIndicateur.getItems().isEmpty()) {
            comboIndicateur.getItems().addAll(indicateurColorMap.keySet());
        }
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setTitre(String titre) {
        champTitre.setText(titre);
    }

    public void setDetails(String details) {
        champDetails.setText(details);
    }

    @FXML
    private void handleDemarrerSimulation() {
        String solutionType = comboSolution.getValue();
        String indicateurType = comboIndicateur.getValue();

        if (solutionType == null || indicateurType == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir une solution et un indicateur.");
            return;
        }

        // Ajouter l'indicateur
        Color indicateurColor = indicateurColorMap.get(indicateurType);
        PhongMaterial materialIndicateur = new PhongMaterial();
        materialIndicateur.setDiffuseColor(indicateurColor);
        materialIndicateur.setSpecularColor(indicateurColor.darker());
        indicateur.setMaterial(materialIndicateur);
        indicateur.setVisible(true);

        // Ajouter la solution
        Color solutionColor = solutionColorMap.get(solutionType);
        PhongMaterial materialSolution = new PhongMaterial();
        materialSolution.setDiffuseColor(solutionColor);
        materialSolution.setSpecularColor(solutionColor.darker());
        solution.setMaterial(materialSolution);
        solution.setVisible(true);

        // Animation de versement de l'indicateur dans la solution
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setNode(becherIndicateur);
        Path path = new Path();
        path.getElements().add(new MoveTo(-100, 0));
        path.getElements().add(new LineTo(0, 0));
        transition.setPath(path);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), becherIndicateur);
        rotateTransition.setByAngle(-45);
        rotateTransition.setAxis(Rotate.Z_AXIS);

        PathTransition dropTransition = new PathTransition();
        dropTransition.setDuration(Duration.seconds(1));
        dropTransition.setNode(indicateur);
        Path dropPath = new Path();
        dropPath.getElements().add(new MoveTo(0, 50));
        dropPath.getElements().add(new LineTo(100, 50));
        dropTransition.setPath(dropPath);

        SequentialTransition sequentialTransition = new SequentialTransition(
                transition,
                rotateTransition,
                dropTransition
        );

        sequentialTransition.setOnFinished(e -> {
            // Changer la couleur de la solution après le mélange
            PhongMaterial material = (PhongMaterial) solution.getMaterial();
            Color solutionColorAfterMix = ((PhongMaterial) indicateur.getMaterial()).getDiffuseColor();
            material.setDiffuseColor(solutionColorAfterMix);
            material.setSpecularColor(solutionColorAfterMix.darker());
            labelResultat.setText("Mélange effectué: " + solutionPHMap.get(solutionType));
        });

        sequentialTransition.play();
    }

    @FXML
    private void handleRetour() {
        // Fermer la fenêtre actuelle
        if (stage != null) {
            stage.close();
        }
    }

    private PerspectiveCamera createCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -300) // Rapprocher la caméra pour voir les objets
        );
        return camera;
    }

    private Cylinder createBecher(int x, int y, int height) {
        Cylinder cylinder = new Cylinder(50, height); // Augmenter la hauteur du bécher
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);
        cylinder.setMaterial(material);
        cylinder.setTranslateX(x);
        cylinder.setTranslateY(y);
        cylinder.setTranslateZ(0); // Assurez-vous que le bécher est bien dans le plan Z
        return cylinder;
    }

    private Cylinder createSolutionCylinder() {
        Cylinder cylinder = new Cylinder(45, 90); // Ajuster la taille de la solution
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.TRANSPARENT);
        cylinder.setMaterial(material);
        return cylinder;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}