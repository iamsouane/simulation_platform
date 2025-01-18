package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.animation.FillTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreerTPSimulationSVTController {

    @FXML
    private TextField titreField;
    @FXML
    private TextField detailsField;
    @FXML
    private StackPane stackPanePlantes;
    @FXML
    private Slider sliderTemperature;
    @FXML
    private Label labelTemperature;
    @FXML
    private Label descriptionTemperature;
    @FXML
    private Slider sliderHumidite;
    @FXML
    private Label labelHumidite;
    @FXML
    private Label descriptionHumidite;
    @FXML
    private Slider sliderLumiere;
    @FXML
    private Label labelLumiere;
    @FXML
    private Label descriptionLumiere;
    @FXML
    private Button btnEnregistrer; // Le bouton pour enregistrer le TP

    private Professeur professeur;

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
    public void initialize() {
        // Vérifier que stackPanePlantes est bien chargé
        if (stackPanePlantes == null) {
            System.err.println("Erreur : stackPanePlantes est null !");
            return;
        }

        // Espacement entre les plantes
        double spacing = 150;
        double baseX = -spacing;
        double y = 50;

        // Création et ajout de 4 plantes
        for (int i = 0; i < 4; i++) {
            Group plante = creerPlante();
            plante.setTranslateX(baseX + i * spacing);
            plante.setTranslateY(y);
            stackPanePlantes.getChildren().add(plante);
        }

        // Gestion des sliders avec animations
        sliderTemperature.valueProperty().addListener((obs, oldVal, newVal) -> {
            labelTemperature.setText(String.format("Température : %.1f°C", newVal.doubleValue()));
            applyTemperatureEffect(stackPanePlantes, newVal.doubleValue());
            updateTemperatureDescription(newVal.doubleValue());
        });

        sliderHumidite.valueProperty().addListener((obs, oldVal, newVal) -> {
            labelHumidite.setText(String.format("Humidité : %.1f%%", newVal.doubleValue()));
            applyHumidityEffect(stackPanePlantes, newVal.doubleValue());
            updateHumidityDescription(newVal.doubleValue());
        });

        sliderLumiere.valueProperty().addListener((obs, oldVal, newVal) -> {
            labelLumiere.setText(String.format("Lumière : %.1f%%", newVal.doubleValue()));
            applyLumiereEffect(stackPanePlantes, newVal.doubleValue());
            updateLumiereDescription(newVal.doubleValue());
        });
    }

    private Group creerPlante() {
        Group plante = new Group();

        // Création de la tige (Cylinder)
        Cylinder tige = new Cylinder(10, 100);
        tige.setMaterial(new javafx.scene.paint.PhongMaterial(Color.SADDLEBROWN));

        // Feuilles sous forme de cercles
        Circle feuille1 = new Circle(20, Color.DARKGREEN);
        feuille1.setTranslateY(-60);
        feuille1.setTranslateX(-15);

        Circle feuille2 = new Circle(20, Color.DARKGREEN);
        feuille2.setTranslateY(-60);
        feuille2.setTranslateX(15);

        // Ajout des éléments au groupe
        plante.getChildren().addAll(tige, feuille1, feuille2);

        return plante;
    }

    private void applyTemperatureEffect(StackPane stackPanePlantes, double temperature) {
        for (var node : stackPanePlantes.getChildren()) {
            if (node instanceof Group) {
                Group plante = (Group) node;
                Circle feuille1 = (Circle) plante.getChildren().get(1);
                Circle feuille2 = (Circle) plante.getChildren().get(2);
                Cylinder tige = (Cylinder) plante.getChildren().get(0);

                // Animation de la couleur des feuilles
                FillTransition fillTransition1 = new FillTransition(Duration.millis(500), feuille1);
                FillTransition fillTransition2 = new FillTransition(Duration.millis(500), feuille2);

                if (temperature < 15) {
                    fillTransition1.setToValue(Color.LIGHTYELLOW);
                    fillTransition2.setToValue(Color.LIGHTYELLOW);
                    fillTransition1.play();
                    fillTransition2.play();

                    // Animation de la taille de la tige
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), tige);
                    scaleTransition.setToX(0.8);
                    scaleTransition.setToY(0.8);
                    scaleTransition.play();
                } else if (temperature <= 25) {
                    fillTransition1.setToValue(Color.DARKGREEN);
                    fillTransition2.setToValue(Color.DARKGREEN);
                    fillTransition1.play();
                    fillTransition2.play();

                    // Animation de la taille de la tige
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), tige);
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    scaleTransition.play();
                } else {
                    fillTransition1.setToValue(Color.BROWN);
                    fillTransition2.setToValue(Color.BROWN);
                    fillTransition1.play();
                    fillTransition2.play();

                    // Animation de la taille de la tige
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), tige);
                    scaleTransition.setToX(0.8);
                    scaleTransition.setToY(0.8);
                    scaleTransition.play();
                }
            }
        }
    }

    private void updateTemperatureDescription(double temperature) {
        if (temperature < 15) {
            descriptionTemperature.setText("Température trop basse, la croissance des plantes est réduite.");
        } else if (temperature <= 25) {
            descriptionTemperature.setText("Température optimale pour la croissance des plantes.");
        } else {
            descriptionTemperature.setText("Température trop élevée, les plantes peuvent souffrir.");
        }
    }

    private void applyHumidityEffect(StackPane stackPanePlantes, double humidite) {
        for (var node : stackPanePlantes.getChildren()) {
            if (node instanceof Group) {
                Group plante = (Group) node;
                Circle feuille1 = (Circle) plante.getChildren().get(1);
                Circle feuille2 = (Circle) plante.getChildren().get(2);

                // Animation de la couleur des feuilles
                FillTransition fillTransition1 = new FillTransition(Duration.millis(500), feuille1);
                FillTransition fillTransition2 = new FillTransition(Duration.millis(500), feuille2);

                if (humidite < 30) {
                    fillTransition1.setToValue(Color.YELLOWGREEN);
                    fillTransition2.setToValue(Color.YELLOWGREEN);
                    fillTransition1.play();
                    fillTransition2.play();
                } else if (humidite <= 70) {
                    fillTransition1.setToValue(Color.DARKGREEN);
                    fillTransition2.setToValue(Color.DARKGREEN);
                    fillTransition1.play();
                    fillTransition2.play();
                } else {
                    fillTransition1.setToValue(Color.DARKOLIVEGREEN);
                    fillTransition2.setToValue(Color.DARKOLIVEGREEN);
                    fillTransition1.play();
                    fillTransition2.play();
                }
            }
        }
    }

    private void updateHumidityDescription(double humidite) {
        if (humidite < 30) {
            descriptionHumidite.setText("Humidité trop faible, les feuilles peuvent jaunir.");
        } else if (humidite <= 70) {
            descriptionHumidite.setText("Humidité idéale pour une croissance saine des plantes.");
        } else {
            descriptionHumidite.setText("Humidité trop élevée, les plantes peuvent être vulnérables à la moisissure.");
        }
    }

    private void applyLumiereEffect(StackPane stackPanePlantes, double lumiere) {
        for (var node : stackPanePlantes.getChildren()) {
            if (node instanceof Group) {
                Group plante = (Group) node;
                Circle feuille1 = (Circle) plante.getChildren().get(1);
                Circle feuille2 = (Circle) plante.getChildren().get(2);

                // Animation de l'opacité des feuilles
                FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(500), feuille1);
                FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), feuille2);

                if (lumiere < 30) {
                    fadeTransition1.setToValue(0.5);
                    fadeTransition2.setToValue(0.5);
                    fadeTransition1.play();
                    fadeTransition2.play();
                } else if (lumiere <= 70) {
                    fadeTransition1.setToValue(1);
                    fadeTransition2.setToValue(1);
                    fadeTransition1.play();
                    fadeTransition2.play();
                } else {
                    fadeTransition1.setToValue(1.2);
                    fadeTransition2.setToValue(1.2);
                    fadeTransition1.play();
                    fadeTransition2.play();
                }
            }
        }
    }

    private void updateLumiereDescription(double lumiere) {
        if (lumiere < 30) {
            descriptionLumiere.setText("Manque de lumière, la photosynthèse est limitée.");
        } else if (lumiere <= 70) {
            descriptionLumiere.setText("Lumière idéale pour une photosynthèse optimale.");
        } else {
            descriptionLumiere.setText("Excès de lumière, la photosynthèse peut être perturbée.");
        }
    }

    @FXML
    public void enregistrerTP() {
        // Récupérer les données des champs du formulaire
        String titre = titreField.getText();
        String details = detailsField.getText();
        String matiere = "SVT"; // On suppose que c'est un TP de SVT, vous pouvez ajouter un choix de matière
        String typeTP = "SIMULATION"; // Idem, vous pouvez ajouter un champ pour choisir le type de TP
        int createurId = professeur.getId(); // Utilisez l'ID du professeur connecté

        // Insérer le TP dans la base de données
        boolean success = enregistrerTPDansBaseDeDonnees(titre, details, matiere, typeTP, createurId);

        if (success) {
            // Afficher une alerte de succès
            showAlert("Succès", "Le TP a été enregistré avec succès.", Alert.AlertType.INFORMATION);
        } else {
            // Afficher une alerte d'erreur
            showAlert("Erreur", "Une erreur est survenue lors de l'enregistrement du TP.", Alert.AlertType.ERROR);
        }
    }

    private boolean enregistrerTPDansBaseDeDonnees(String titre, String details, String matiere, String typeTP, int createurId) {
        String url = "jdbc:mysql://localhost:3306/simulation_platform";
        String user = "root";
        String password = "1234"; // Remplacez par votre mot de passe

        String sql = "INSERT INTO tp (titre, details, matiere, typeTP, createur) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, titre);
            stmt.setString(2, details);
            stmt.setString(3, matiere);
            stmt.setString(4, typeTP);
            stmt.setInt(5, createurId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourne true si l'insertion a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourne false en cas d'erreur
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
