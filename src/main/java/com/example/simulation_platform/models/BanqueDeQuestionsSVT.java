package com.example.simulation_platform.models;

import java.util.ArrayList;
import java.util.List;

public class BanqueDeQuestionsSVT {
    private List<Question> questions;

    public BanqueDeQuestionsSVT() {
        questions = new ArrayList<>();
        // Ajoutez quelques questions de base avec des réponses
        List<Reponse> reponses1 = new ArrayList<>();
        reponses1.add(new Reponse(1, "La photosynthèse", true));
        reponses1.add(new Reponse(2, "La respiration", false));
        reponses1.add(new Reponse(3, "La fermentation", false));
        reponses1.add(new Reponse(4, "La digestion", false));
        questions.add(new Question(1, "Quelle est la fonction des chloroplastes ?", reponses1));

        List<Reponse> reponses2 = new ArrayList<>();
        reponses2.add(new Reponse(1, "L'ADN", true));
        reponses2.add(new Reponse(2, "L'ARN", false));
        reponses2.add(new Reponse(3, "Les protéines", false));
        reponses2.add(new Reponse(4, "Les lipides", false));
        questions.add(new Question(2, "Qu'est-ce qui porte l'information génétique ?", reponses2));

        List<Reponse> reponses3 = new ArrayList<>();
        reponses3.add(new Reponse(1, "Les mitochondries", true));
        reponses3.add(new Reponse(2, "Les ribosomes", false));
        reponses3.add(new Reponse(3, "Le noyau", false));
        reponses3.add(new Reponse(4, "Les vacuoles", false));
        questions.add(new Question(3, "Quelle organite est responsable de la production d'énergie ?", reponses3));
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void ajouterQuestion(Question question) {
        questions.add(question);
    }

    public void supprimerQuestion(Question question) {
        questions.remove(question);
    }
}