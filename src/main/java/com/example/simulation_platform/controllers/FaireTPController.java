package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FaireTPController {

    private Stage stage;
    private Eleve eleve;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    @FXML
    private void handleFaireTPQuizzChimie() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_quizz_chimie.fxml");
    }

    @FXML
    private void handleFaireTPQuizzSVT() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_quizz_svt.fxml");
    }

    @FXML
    private void handleFaireTPSimulationChimie() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_simulation_chimie.fxml");
    }

    @FXML
    private void handleFaireTPSimulationSVT() {
        loadFXML("/com/example/simulation_platform/views/faire_tp_simulation_svt.fxml");
    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            VBox vbox = loader.load();
            Scene scene = new Scene(vbox);

            // Vérifiez si `stage` est `null` avant d'appeler `setScene`
            if (stage != null) {
                stage.setScene(scene);
                stage.setTitle("Faire TP - Simulation Platform");
            } else {
                System.err.println("Stage is null. Cannot set scene.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}