package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsulterSesResultatsController {

    private Eleve eleve;
    private Stage stage;

    @FXML
    private VBox resultsVBox;

    @FXML
    private Label resultsLabel;

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
        loadResultats();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadResultats() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM resultat WHERE eleve = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, eleve.getId());
            ResultSet resultSet = statement.executeQuery();
            StringBuilder resultats = new StringBuilder("Résultats :\n");

            while (resultSet.next()) {
                int note = resultSet.getInt("note");
                String commentaires = resultSet.getString("commentaires");
                resultats.append("TP: ").append(resultSet.getInt("tp")).append("\n");
                resultats.append("Note: ").append(note).append("/20\n");
                resultats.append("Commentaires: ").append(commentaires).append("\n\n");
            }

            resultsLabel.setText(resultats.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            resultsLabel.setText("Une erreur est survenue lors de la récupération des résultats.");
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/eleve_view.fxml"));
            VBox eleveView = loader.load();
            EleveController controller = loader.getController();
            controller.setEleve(eleve);
            controller.setStage(stage);
            Scene scene = new Scene(eleveView);
            stage.setScene(scene);
            stage.setTitle("Liste des TP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
