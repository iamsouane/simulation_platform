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

        // Ajouter les nouvelles questions
        ajouterNouvellesQuestions();
    }

    private void ajouterNouvellesQuestions() {
        List<Reponse> reponses4 = new ArrayList<>();
        reponses4.add(new Reponse(1, "Biomasse", true));
        reponses4.add(new Reponse(2, "Productivité", false));
        reponses4.add(new Reponse(3, "Énergie trophique", false));
        reponses4.add(new Reponse(4, "Écosystème", false));
        questions.add(new Question(4, "Quel terme désigne la masse totale de matière vivante dans un écosystème ?", reponses4));

        List<Reponse> reponses5 = new ArrayList<>();
        reponses5.add(new Reponse(1, "La lumière", false));
        reponses5.add(new Reponse(2, "Le sol", true));
        reponses5.add(new Reponse(3, "La température", false));
        reponses5.add(new Reponse(4, "La disponibilité de l'eau", false));
        questions.add(new Question(5, "Les facteurs édaphiques sont liés à :", reponses5));

        List<Reponse> reponses6 = new ArrayList<>();
        reponses6.add(new Reponse(1, "Les carnivores", false));
        reponses6.add(new Reponse(2, "Les consommateurs primaires", false));
        reponses6.add(new Reponse(3, "Les producteurs", true));
        reponses6.add(new Reponse(4, "Les décomposeurs", false));
        questions.add(new Question(6, "Dans une pyramide écologique, la base est occupée par :", reponses6));

        List<Reponse> reponses7 = new ArrayList<>();
        reponses7.add(new Reponse(1, "L'humidité", false));
        reponses7.add(new Reponse(2, "La température", false));
        reponses7.add(new Reponse(3, "L'ensoleillement", true));
        reponses7.add(new Reponse(4, "La pression atmosphérique", false));
        questions.add(new Question(7, "Quel facteur climatique a un impact direct sur la photosynthèse des plantes ?", reponses7));

        List<Reponse> reponses8 = new ArrayList<>();
        reponses8.add(new Reponse(1, "La stabilité des espèces", false));
        reponses8.add(new Reponse(2, "La variation des êtres vivants au fil du temps", true));
        reponses8.add(new Reponse(3, "La disparition des facteurs écologiques", false));
        reponses8.add(new Reponse(4, "La formation des écosystèmes", false));
        questions.add(new Question(8, "La théorie de l'évolution explique principalement :", reponses8));

        List<Reponse> reponses9 = new ArrayList<>();
        reponses9.add(new Reponse(1, "Une espèce est un groupe d'individus identiques.", false));
        reponses9.add(new Reponse(2, "Une espèce regroupe des individus capables de se reproduire entre eux et de donner une descendance fertile.", true));
        reponses9.add(new Reponse(3, "Une espèce regroupe des organismes vivant dans le même habitat.", false));
        reponses9.add(new Reponse(4, "Une espèce ne subit aucune variation au cours du temps.", false));
        questions.add(new Question(9, "Quelle affirmation est correcte ?", reponses9));

        List<Reponse> reponses10 = new ArrayList<>();
        reponses10.add(new Reponse(1, "Pétrole", false));
        reponses10.add(new Reponse(2, "Roches phosphatées", true));
        reponses10.add(new Reponse(3, "Charbon", false));
        reponses10.add(new Reponse(4, "Or", false));
        questions.add(new Question(10, "Quelle ressource naturelle est abondante dans la géologie du Sénégal ?", reponses10));

        List<Reponse> reponses11 = new ArrayList<>();
        reponses11.add(new Reponse(1, "Une forte densité de population", false));
        reponses11.add(new Reponse(2, "Une prédominance des activités agricoles", true));
        reponses11.add(new Reponse(3, "Une forte urbanisation", false));
        reponses11.add(new Reponse(4, "Une absence de biodiversité", false));
        questions.add(new Question(11, "Un espace rural est caractérisé principalement par :", reponses11));

        List<Reponse> reponses12 = new ArrayList<>();
        reponses12.add(new Reponse(1, "Le pétrole", false));
        reponses12.add(new Reponse(2, "L'énergie solaire", true));
        reponses12.add(new Reponse(3, "Le charbon", false));
        reponses12.add(new Reponse(4, "L'énergie nucléaire", false));
        questions.add(new Question(12, "Quelle est la principale source d’énergie renouvelable ?", reponses12));

        List<Reponse> reponses13 = new ArrayList<>();
        reponses13.add(new Reponse(1, "Les infrastructures humaines", false));
        reponses13.add(new Reponse(2, "Les interactions entre les êtres vivants et leur milieu", true));
        reponses13.add(new Reponse(3, "Les circuits d’énergie industrielle", false));
        reponses13.add(new Reponse(4, "Les modèles économiques locaux", false));
        questions.add(new Question(13, "Lors d’une sortie écologique, on observe principalement :", reponses13));
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