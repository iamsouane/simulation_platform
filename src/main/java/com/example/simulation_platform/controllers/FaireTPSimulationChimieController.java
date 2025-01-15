package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.TP;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class FaireTPSimulationChimieController {
    @FXML
    private StackPane simulationContainer;
    @FXML
    private ComboBox<String> solutionComboBox;
    @FXML
    private ComboBox<String> indicateurComboBox;
    @FXML
    private Text resultatText;

    private Professeur professeur;
    private Stage stage;
    private Eleve eleve;
    private TP tp;

    private Map<String, Color> solutionsMap;
    private Map<String, Color> indicateursMap;
    private Map<String, Map<String, Color>> solutionIndicateurColorsMap;

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public void setTP(TP tp) {
        this.tp = tp;
    }

    private String getColorName(Color color) {
        if (color.equals(Color.VIOLET)) {
            return "Violet";
        } else if (color.equals(Color.RED)) {
            return "Rouge";
        } else if (color.equals(Color.GREEN)) {
            return "Vert";
        } else if (color.equals(Color.YELLOW)) {
            return "Jaune";
        } else if (color.equals(Color.PINK)) {
            return "Rose";
        } else if (color.equals(Color.BLUE)) {
            return "Bleu";
        } else if (color.equals(Color.ORANGE)) {
            return "Orange";
        } else if (color.equals(Color.TRANSPARENT)) {
            return "Transparent";
        } else {
            return "Inconnue";
        }
    }

    @FXML
    private void initialize() {
        solutionsMap = new HashMap<>();
        solutionsMap.put("Eau distillée (neutre)", Color.VIOLET);
        solutionsMap.put("Vinaigre (acide)", Color.RED);
        solutionsMap.put("Eau savonneuse (basique)", Color.GREEN);
        solutionsMap.put("Jus de citron (acide)", Color.RED);
        solutionsMap.put("Bicarbonate de soude (basique)", Color.YELLOW);

        indicateursMap = new HashMap<>();
        indicateursMap.put("Phénolphtaléine", Color.PINK);
        indicateursMap.put("Bleu de bromothymol", Color.BLUE);
        indicateursMap.put("Jus de chou rouge", Color.VIOLET);
        indicateursMap.put("Rouge de méthyle", Color.ORANGE);

        solutionIndicateurColorsMap = new HashMap<>();

        Map<String, Color> eauDistilleColors = new HashMap<>();
        eauDistilleColors.put("Phénolphtaléine", Color.TRANSPARENT);
        eauDistilleColors.put("Bleu de bromothymol", Color.GREEN);
        eauDistilleColors.put("Jus de chou rouge", Color.VIOLET);
        eauDistilleColors.put("Rouge de méthyle", Color.ORANGE);
        solutionIndicateurColorsMap.put("Eau distillée (neutre)", eauDistilleColors);

        Map<String, Color> vinaigreColors = new HashMap<>();
        vinaigreColors.put("Phénolphtaléine", Color.TRANSPARENT);
        vinaigreColors.put("Bleu de bromothymol", Color.YELLOW);
        vinaigreColors.put("Jus de chou rouge", Color.RED);
        vinaigreColors.put("Rouge de méthyle", Color.RED);
        solutionIndicateurColorsMap.put("Vinaigre (acide)", vinaigreColors);

        Map<String, Color> eauSavonneuseColors = new HashMap<>();
        eauSavonneuseColors.put("Phénolphtaléine", Color.PINK);
        eauSavonneuseColors.put("Bleu de bromothymol", Color.BLUE);
        eauSavonneuseColors.put("Jus de chou rouge", Color.GREEN);
        eauSavonneuseColors.put("Rouge de méthyle", Color.YELLOW);
        solutionIndicateurColorsMap.put("Eau savonneuse (basique)", eauSavonneuseColors);

        Map<String, Color> jusDeCitronColors = new HashMap<>();
        jusDeCitronColors.put("Phénolphtaléine", Color.TRANSPARENT);
        jusDeCitronColors.put("Bleu de bromothymol", Color.YELLOW);
        jusDeCitronColors.put("Jus de chou rouge", Color.RED);
        jusDeCitronColors.put("Rouge de méthyle", Color.RED);
        solutionIndicateurColorsMap.put("Jus de citron (acide)", jusDeCitronColors);

        Map<String, Color> bicarbonateColors = new HashMap<>();
        bicarbonateColors.put("Phénolphtaléine", Color.PINK);
        bicarbonateColors.put("Bleu de bromothymol", Color.BLUE);
        bicarbonateColors.put("Jus de chou rouge", Color.GREEN);
        bicarbonateColors.put("Rouge de méthyle", Color.YELLOW);
        solutionIndicateurColorsMap.put("Bicarbonate de soude (basique)", bicarbonateColors);

        solutionComboBox.getItems().addAll(solutionsMap.keySet());
        indicateurComboBox.getItems().addAll(indicateursMap.keySet());
    }

    @FXML
    private void handleCreer() {
        String selectedSolution = solutionComboBox.getValue();
        String selectedIndicateur = indicateurComboBox.getValue();
        if (selectedIndicateur == null || selectedSolution == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir un indicateur et une solution.");
            return;
        }

        create3DSimulation(selectedSolution, selectedIndicateur);
    }

    @FXML
    private void create3DSimulation(String selectedSolution, String selectedIndicateur) {
        Cylinder becher1 = createBecher(-50);
        Cylinder becher2 = createBecher(50);

        if (selectedIndicateur != null) {
            Color color = indicateursMap.get(selectedIndicateur);
            setBecherColor(becher1, color);
        }

        if (selectedSolution != null) {
            Color solutionColor = solutionsMap.get(selectedSolution);
            setBecherColor(becher2, solutionColor);
        }

        Text becher1Label = createFixedLabel(-30, 50, "Bécher 1: \nindicateur");
        Text becher2Label = createFixedLabel(70, 50, "Bécher 2: \nsolution");

        Rectangle fond = new Rectangle(400, 400);
        fond.setFill(Color.CORNFLOWERBLUE);
        fond.setTranslateZ(-1);

        simulationContainer.getChildren().addAll(fond, becher1, becher2, becher1Label, becher2Label);
        moveBecherToOther(becher1, becher2, selectedSolution, selectedIndicateur);
        afficherResultat(selectedSolution, selectedIndicateur);
    }

    @FXML
    private void afficherResultat(String selectedSolution, String selectedIndicateur) {
        StringBuilder description = new StringBuilder();

        if (selectedSolution != null) {
            if (selectedSolution.contains("acide")) {
                description.append("La solution est acide\n");
            } else if (selectedSolution.contains("basique")) {
                description.append("La solution est basique\n");
            } else {
                description.append("La solution est neutre\n");
            }

            Color finalColor = solutionIndicateurColorsMap.get(selectedSolution).get(selectedIndicateur);
            String couleurFinale = getColorName(finalColor);
            description.append("Couleur: ").append(couleurFinale).append("\n");

            // Affichage du pH attendu
            if ("Phénolphtaléine".equals(selectedIndicateur)) {
                description.append(finalColor.equals(Color.TRANSPARENT)
                        ? "pH: < 8,2 (Acide ou Neutre)"
                        : "pH: > 8,2 (Basique)");
            } else if ("Bleu de bromothymol".equals(selectedIndicateur)) {
                description.append(finalColor.equals(Color.YELLOW)
                        ? "pH: < 7 (Acide)"
                        : finalColor.equals(Color.GREEN)
                        ? "pH: 7 (Neutre)"
                        : "pH: > 7 (Basique)");
            } else if ("Jus de chou rouge".equals(selectedIndicateur)) {
                description.append(finalColor.equals(Color.RED)
                        ? "pH: < 7 (Acide)"
                        : finalColor.equals(Color.VIOLET)
                        ? "pH: 7 (Neutre)"
                        : "pH: > 7 (Basique)");
            } else if ("Rouge de méthyle".equals(selectedIndicateur)) {
                description.append(finalColor.equals(Color.RED)
                        ? "pH: < 4,4 (Acide)"
                        : "pH: > 6,2 (Basique)");
            } else {
                description.append("pH: Indéterminé pour cet indicateur");
            }
        }

        resultatText.setText(description.toString());
    }


    @FXML
    private Cylinder createBecher(double positionX) {
        Cylinder becher = new Cylinder(20, 100);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));
        becher.setMaterial(material);
        becher.setTranslateX(positionX);
        return becher;
    }

    @FXML
    private void setBecherColor(Cylinder becher, Color color) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        becher.setMaterial(material);
    }

    @FXML
    private Text createFixedLabel(double positionX, double offsetY, String label) {
        Text textLabel = new Text(label);
        textLabel.setFill(Color.BLACK);
        textLabel.setTranslateX(positionX - 15);
        textLabel.setTranslateY(50 + offsetY);
        return textLabel;
    }

    @FXML
    private void moveBecherToOther(Cylinder becher1, Cylinder becher2, String selectedSolution, String selectedIndicateur) {
        Path path = new Path();
        path.getElements().add(new MoveTo(becher1.getTranslateX(), becher1.getTranslateY()));

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
            if (selectedIndicateur != null && selectedSolution != null) {
                animateBecherColor(becher2, selectedSolution, selectedIndicateur);
            }

            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.rgb(192, 192, 192, 0.2));
            becher1.setMaterial(material);
        });

        pathTransition1.play();
    }

    @FXML
    private void animateBecherColor(Cylinder becher, String selectedSolution, String selectedIndicateur) {
        Color solutionColor = solutionsMap.get(selectedSolution);
        Color indicatorColor = indicateursMap.get(selectedIndicateur);
        Color finalColor = solutionIndicateurColorsMap.get(selectedSolution).get(selectedIndicateur);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(finalColor);
        becher.setMaterial(material);

        Timeline timeline = new Timeline();
        for (int i = 0; i <= 100; i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 50), event -> {
                material.setDiffuseColor(solutionColor.interpolate(finalColor, index / 100.0));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
