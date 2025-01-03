package com.example.simulation_platform.models;

import java.util.ArrayList;
import java.util.List;

public class BanqueDeQuestionsChimie {
    private List<Question> questions;

    public BanqueDeQuestionsChimie() {
        questions = new ArrayList<>();
        // Ajoutez quelques questions de base avec des réponses
        List<Reponse> reponses1 = new ArrayList<>();
        reponses1.add(new Reponse(1, "H2O", true));
        reponses1.add(new Reponse(2, "CO2", false));
        reponses1.add(new Reponse(3, "O2", false));
        reponses1.add(new Reponse(4, "H2", false));
        questions.add(new Question(1, "Quelle est la formule chimique de l'eau ?", reponses1));

        List<Reponse> reponses2 = new ArrayList<>();
        reponses2.add(new Reponse(1, "16 g/mol", true));
        reponses2.add(new Reponse(2, "18 g/mol", false));
        reponses2.add(new Reponse(3, "14 g/mol", false));
        reponses2.add(new Reponse(4, "12 g/mol", false));
        questions.add(new Question(2, "Quelle est la masse molaire de l'oxygène ?", reponses2));

        List<Reponse> reponses3 = new ArrayList<>();
        reponses3.add(new Reponse(1, "Une réaction qui libère de la chaleur", true));
        reponses3.add(new Reponse(2, "Une réaction qui absorbe de la chaleur", false));
        reponses3.add(new Reponse(3, "Une réaction neutre", false));
        reponses3.add(new Reponse(4, "Aucune de ces réponses", false));
        questions.add(new Question(3, "Qu'est-ce qu'une réaction exothermique ?", reponses3));
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