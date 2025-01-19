package com.example.simulation_platform.controllers;

import com.example.simulation_platform.models.Eleve;
import com.example.simulation_platform.models.Professeur;
import com.example.simulation_platform.models.Resultat;
import com.example.simulation_platform.models.TP;
import com.example.simulation_platform.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsulterResultatsEleveController {

    @FXML
    private TableView<Resultat> resultatsTable;
    @FXML
    private TableColumn<Resultat, String> tpColumn;
    @FXML
    private TableColumn<Resultat, String> eleveColumn;
    @FXML
    private TableColumn<Resultat, Integer> noteColumn;
    @FXML
    private TableColumn<Resultat, String> commentairesColumn;

    private Professeur professeur;
    private Stage stage;

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Associer les colonnes avec les propriétés du modèle Resultat
        tpColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTp().getTitre()));
        eleveColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEleve().getPrenom() + " " + cellData.getValue().getEleve().getNom()));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        commentairesColumn.setCellValueFactory(new PropertyValueFactory<>("commentaires"));
    }

    public void loadResultats() {
        ObservableList<Resultat> resultatsList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = """
                SELECT r.note, r.commentaires, t.idTP, t.titre, u.idUtilisateur, u.nom, u.prenom
                FROM resultat r
                JOIN tp t ON r.tp = t.idTP
                JOIN utilisateur u ON r.eleve = u.idUtilisateur
                WHERE t.createur = ?;
                """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, professeur.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idTP = resultSet.getInt("idTP");
                String tpTitre = resultSet.getString("titre");
                TP tp = new TP(tpTitre, "", null, null, professeur); // Les autres attributs sont mis à null pour éviter les erreurs

                int idEleve = resultSet.getInt("idUtilisateur");
                String eleveNom = resultSet.getString("nom");
                String elevePrenom = resultSet.getString("prenom");
                Eleve eleve = new Eleve(idEleve, eleveNom, elevePrenom, "", ""); // Email et mot de passe sont inutiles ici

                int note = resultSet.getInt("note");
                String commentaires = resultSet.getString("commentaires");

                Resultat resultat = new Resultat(eleve, tp);
                resultat.setNote(note);
                resultat.setCommentaires(commentaires);

                resultatsList.add(resultat);
            }

            resultatsTable.setItems(resultatsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_platform/views/professeur_view.fxml"));
            Scene scene = new Scene(loader.load());

            ProfesseurController controller = loader.getController();
            controller.setProfesseur(professeur);
            controller.setStage(stage);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
