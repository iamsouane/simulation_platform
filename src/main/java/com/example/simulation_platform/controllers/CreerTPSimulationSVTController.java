package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Professeur;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;

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
    private Slider sliderHumidite;

    @FXML
    private Label labelHumidite;

    @FXML
    private Slider sliderLumiere;

    @FXML
    private Label labelLumiere;

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

        // Gestion des sliders
        sliderTemperature.valueProperty().addListener((obs, oldVal, newVal) -> {
            labelTemperature.setText(String.format("Température : %.1f°C", newVal.doubleValue()));
            // Appliquer l'effet de température aux plantes
            applyTemperatureEffect(stackPanePlantes, newVal.doubleValue());
        });

        sliderHumidite.valueProperty().addListener((obs, oldVal, newVal) -> {
            labelHumidite.setText(String.format("Humidité : %.1f%%", newVal.doubleValue()));
            // Appliquer l'effet d'humidité aux plantes (changer la couleur)
            applyHumidityEffect(stackPanePlantes, newVal.doubleValue());
        });

        sliderLumiere.valueProperty().addListener((obs, oldVal, newVal) -> {
            labelLumiere.setText(String.format("Lumière : %.1f%%", newVal.doubleValue()));
            // Appliquer l'effet de lumière aux plantes (influence photosynthèse)
            applyLumiereEffect(stackPanePlantes, newVal.doubleValue());
        });
    }

    private Group creerPlante() {
        Group plante = new Group();

        // Création de la tige (Cylinder)
        Cylinder tige = new Cylinder(10, 100);
        tige.setMaterial(new javafx.scene.paint.PhongMaterial(Color.SADDLEBROWN));

        // Feuilles sous forme de cercles
        Circle feuille1 = new Circle(20, Color.DARKGREEN); // couleur initiale
        feuille1.setTranslateY(-60);
        feuille1.setTranslateX(-15);

        Circle feuille2 = new Circle(20, Color.DARKGREEN); // couleur initiale
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

                if (temperature < 15) {
                    feuille1.setFill(Color.LIGHTYELLOW);  // Température basse
                    feuille2.setFill(Color.LIGHTYELLOW);
                } else if (temperature <= 25) {
                    feuille1.setFill(Color.DARKGREEN);  // Température optimale
                    feuille2.setFill(Color.DARKGREEN);
                } else {
                    feuille1.setFill(Color.BROWN);  // Température élevée
                    feuille2.setFill(Color.BROWN);
                }
            }
        }
    }

    private void applyHumidityEffect(StackPane stackPanePlantes, double humidite) {
        for (var node : stackPanePlantes.getChildren()) {
            if (node instanceof Group) {
                Group plante = (Group) node;
                Circle feuille1 = (Circle) plante.getChildren().get(1);
                Circle feuille2 = (Circle) plante.getChildren().get(2);

                // Modifie la couleur des feuilles en fonction de l'humidité
                if (humidite < 30) {
                    feuille1.setFill(Color.YELLOWGREEN); // Feuilles sèches (très faible humidité)
                    feuille2.setFill(Color.YELLOWGREEN); // Feuilles sèches (très faible humidité)
                } else if (humidite <= 70) {
                    feuille1.setFill(Color.DARKGREEN); // Feuilles bien hydratées (haute humidité)
                    feuille2.setFill(Color.DARKGREEN); // Feuilles bien hydratées (haute humidité)
                } else {
                    feuille1.setFill(Color.DARKOLIVEGREEN); // Humidité moyenne
                    feuille2.setFill(Color.DARKOLIVEGREEN); // Humidité moyenne
                }
            }
        }
    }

    private void applyLumiereEffect(StackPane stackPanePlantes, double lumiere) {
        for (var node : stackPanePlantes.getChildren()) {
            if (node instanceof Group) {
                Group plante = (Group) node;
                Circle feuille1 = (Circle) plante.getChildren().get(1);
                Circle feuille2 = (Circle) plante.getChildren().get(2);

                // Influence de la lumière sur la photosynthèse
                if (lumiere < 30) {
                    feuille1.setOpacity(0.5);
                    feuille2.setOpacity(0.5);
                } else if (lumiere <= 70) {
                    feuille1.setOpacity(1);
                    feuille2.setOpacity(1);
                } else {
                    feuille1.setOpacity(1.2);  // Effet d'excès de lumière (légèrement plus lumineux)
                    feuille2.setOpacity(1.2);
                }
            }
        }
    }
}
