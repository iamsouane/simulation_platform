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
    private Label descriptionTemperature;  // Label pour décrire l'effet de la température

    @FXML
    private Slider sliderHumidite;

    @FXML
    private Label labelHumidite;

    @FXML
    private Label descriptionHumidite;  // Label pour décrire l'effet de l'humidité

    @FXML
    private Slider sliderLumiere;

    @FXML
    private Label labelLumiere;

    @FXML
    private Label descriptionLumiere;  // Label pour décrire l'effet de la lumière

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
                Cylinder tige = (Cylinder) plante.getChildren().get(0);

                // Effet sur les feuilles en fonction de la température
                if (temperature < 15) {
                    feuille1.setFill(Color.LIGHTYELLOW);  // Température basse
                    feuille2.setFill(Color.LIGHTYELLOW);

                    // Réduction de la taille de la plante (tige) si la température est trop élevée
                    tige.setHeight(80);  // Réduire la hauteur de la tige
                    tige.setRadius(8);   // Réduire le rayon de la tige
                } else if (temperature <= 25) {
                    feuille1.setFill(Color.DARKGREEN);  // Température optimale
                    feuille2.setFill(Color.DARKGREEN);

                    tige.setHeight(100);
                    tige.setRadius(10);
                } else {
                    feuille1.setFill(Color.BROWN);  // Température élevée
                    feuille2.setFill(Color.BROWN);

                    tige.setHeight(100);
                    tige.setRadius(10);
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

                // Modifie la couleur des feuilles en fonction de l'humidité
                if (humidite < 30) {
                    feuille1.setFill(Color.YELLOWGREEN);
                    feuille2.setFill(Color.YELLOWGREEN);
                } else if (humidite <= 70) {
                    feuille1.setFill(Color.DARKGREEN);
                    feuille2.setFill(Color.DARKGREEN);
                } else {
                    feuille1.setFill(Color.DARKOLIVEGREEN);
                    feuille2.setFill(Color.DARKOLIVEGREEN);
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

    private void updateLumiereDescription(double lumiere) {
        if (lumiere < 30) {
            descriptionLumiere.setText("Manque de lumière, la photosynthèse est limitée.");
        } else if (lumiere <= 70) {
            descriptionLumiere.setText("Lumière idéale pour une photosynthèse optimale.");
        } else {
            descriptionLumiere.setText("Excès de lumière, la photosynthèse peut être perturbée.");
        }
    }
}
