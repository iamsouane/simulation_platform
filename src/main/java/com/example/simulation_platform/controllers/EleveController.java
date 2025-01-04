package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EleveController {

    private Stage stage;
    private Eleve eleve;

    @FXML
    private VBox mainVBox;

    @FXML
    private Label welcomeLabel;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    @FXML
    private void handleFaireTP() {
        // Logique pour gérer l'action du bouton "Faire TP"
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/faire_tp_view.fxml"));
            VBox faireTpView = loader.load();
            Scene scene = new Scene(faireTpView);

            FaireTPController controller = loader.getController();
            controller.setStage(stage); // Assurez-vous que `stage` est passé ici
            controller.setEleve(eleve);

            stage.setScene(scene);
            stage.setTitle("Faire TP - Simulation Platform");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConsulterHistorique() {
        // Logique pour gérer l'action du bouton "Consulter Historique"
        System.out.println("Consulter Historique button clicked");
        // Vous pouvez ajouter le code pour charger une nouvelle vue ici
    }

    @FXML
    private void handleLogout() {
        // Logique pour gérer l'action du bouton "Se Déconnecter"
        System.out.println("Logout button clicked");
        // Vous pouvez ajouter le code pour gérer la déconnexion ici
    }
}