package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreerTPSimulationChimieController {
    @FXML
    private StackPane simulationContainer;
    @FXML
    private ComboBox<String> solutionComboBox;
    @FXML
    private ComboBox<String> indicateurComboBox;
    @FXML
    private Text resultatText;

    private Professeur professeur;
    private String titre;
    private String details;
    private Map<String, Color> solutionsMap;
    private Map<String, Color> indicateursMap;
    private Map<String, Map<String, Color>> solutionIndicateurColorsMap;

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

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDetails(String details) {
        this.details = details;
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
    private void handleSoumettre() {
        String selectedSolution = solutionComboBox.getValue();
        String selectedIndicateur = indicateurComboBox.getValue();

        if (selectedSolution == null || selectedIndicateur == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Insérer dans la table tp
            String insertTpQuery = "INSERT INTO tp (titre, details, matiere, typeTP, createur) VALUES (?, ?, 'CHIMIE', 'SIMULATION', ?)";
            PreparedStatement insertTpStatement = connection.prepareStatement(insertTpQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            insertTpStatement.setString(1, titre);
            insertTpStatement.setString(2, details);
            insertTpStatement.setInt(3, professeur.getId());
            insertTpStatement.executeUpdate();

            // Récupérer l'ID du TP créé
            ResultSet generatedKeys = insertTpStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idTp = generatedKeys.getInt(1);

                // Insérer dans la table simulation
                String insertSimulationQuery = "INSERT INTO simulation (idTP) VALUES (?)";
                PreparedStatement insertSimulationStatement = connection.prepareStatement(insertSimulationQuery);
                insertSimulationStatement.setInt(1, idTp);
                insertSimulationStatement.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Simulation et résultat enregistrés avec succès.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'enregistrement du TP et de la simulation.");
        }
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
        String description = "";

        if (selectedSolution != null) {
            if (selectedSolution.contains("acide")) {
                description += "Resultat de la solution:\n";
            } else if (selectedSolution.contains("basique")) {
                description += "Resultat de la solution:\n";
            } else {
                description += "Resultat de la solution:\n";
            }

            Color finalColor = solutionIndicateurColorsMap.get(selectedSolution).get(selectedIndicateur);
            String couleurFinale = getColorName(finalColor);
            description += "Couleur: " + couleurFinale + "\n";

            if ("Phénolphtaléine".equals(selectedIndicateur)) {
                if (finalColor.equals(Color.TRANSPARENT)) {
                    description += "pH: < 8,2 (La solution est donc Acide ou Neutre)";
                } else if (finalColor.equals(Color.PINK)) {
                    description += "pH: > 8,2 (La solution est donc Basique)";
                }
            } else if ("Bleu de bromothymol".equals(selectedIndicateur)) {
                if (finalColor.equals(Color.YELLOW)) {
                    description += "pH: < 7 (La solution est donc Acide)";
                } else if (finalColor.equals(Color.GREEN)) {
                    description += "pH: 7 (La solution est donc Neutre)";
                } else if (finalColor.equals(Color.BLUE)) {
                    description += "pH: > 7 (La solution est donc Basique)";
                }
            } else if ("Jus de chou rouge".equals(selectedIndicateur)) {
                if (finalColor.equals(Color.RED)) {
                    description += "pH: < 7 (La solution est donc Acide)";
                } else if (finalColor.equals(Color.VIOLET)) {
                    description += "pH: 7 (La solution est donc Neutre)";
                } else if (finalColor.equals(Color.GREEN) || finalColor.equals(Color.BLUE)) {
                    description += "pH: > 7 (La solution est donc Basique)";
                }
            } else if ("Rouge de méthyle".equals(selectedIndicateur)) {
                if (finalColor.equals(Color.RED)) {
                    description += "pH: < 4,4 (La solution est donc Acide)";
                } else if (finalColor.equals(Color.YELLOW)) {
                    description += "pH: > 6,2 (La solution est donc Basique)";
                }
            } else {
                description += "pH: Indéterminé pour cet indicateur";
            }
        }

        resultatText.setText(description);
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
    private void handleRetour() {
        try {
            // Charger la vue de retour vers la page de création de TP
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/creer_tp.fxml"));
            Scene scene = new Scene(loader.load());

            // Récupérer le contrôleur de la vue de création de TP
            CreerTPController controller = loader.getController();

            // Passer les informations nécessaires au contrôleur
            controller.setProfesseur(professeur);  // Transférer le professeur
            controller.setStage((Stage) resultatText.getScene().getWindow());  // Passer le stage actuel

            // Mettre à jour la scène avec la vue de création de TP
            Stage stage = (Stage) resultatText.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la vue de création de TP.");
        }
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