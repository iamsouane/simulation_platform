package com.example.simulation_platform.models;

import com.example.simulation_platform.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Professeur extends Utilisateur {
    private List<TP> listeTP;

    public Professeur(int idUtilisateur, String nom, String prenom, String email, String motDePasse) {
        super(idUtilisateur, nom, prenom, email, motDePasse, Role.PROFESSEUR);
        this.listeTP = new ArrayList<>();
    }

    public int getId() {
        return getIdUtilisateur();
    }

    public TP creerTP(String titre, String details, Matiere matiere, TypeTP typeTP) {
        TP tp = new TP(titre, details, matiere, typeTP, this);
        listeTP.add(tp);
        return tp;
    }

    public List<Resultat> consulterResultats(TP tp) {
        return tp.getResultats();
    }

    @Override
    public void sInscrire() {
        // Implementation
    }

    @Override
    public void seConnecter() {
        // Implementation
    }

    @Override
    public void seDeconnecter() {
        // Implementation
    }

    // Méthode pour charger un professeur depuis la base de données
    public static Professeur loadProfesseurFromDatabase(int idUtilisateur) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM utilisateur WHERE idUtilisateur = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUtilisateur);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String motDePasse = resultSet.getString("motDePasse");
                return new Professeur(idUtilisateur, nom, prenom, email, motDePasse);
            }
        }
        return null;
    }
}