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

        // Ajouter les nouvelles questions
        ajouterNouvellesQuestions();
    }

    private void ajouterNouvellesQuestions() {
        List<Reponse> reponses4 = new ArrayList<>();
        reponses4.add(new Reponse(1, "Une substance composée d’un seul type de molécule.", false));
        reponses4.add(new Reponse(2, "Une substance composée de plusieurs types de molécules.", true));
        reponses4.add(new Reponse(3, "Une substance qui ne contient aucun atome.", false));
        reponses4.add(new Reponse(4, "Une substance toujours solide.", false));
        questions.add(new Question(4, "Un mélange est :", reponses4));

        List<Reponse> reponses5 = new ArrayList<>();
        reponses5.add(new Reponse(1, "Le même nombre de protons.", false));
        reponses5.add(new Reponse(2, "Le même nombre de couches électroniques.", false));
        reponses5.add(new Reponse(3, "Le même nombre d’électrons de valence.", true));
        reponses5.add(new Reponse(4, "La même masse atomique.", false));
        questions.add(new Question(5, "Dans la classification périodique, les éléments d’une même colonne ont en commun :", reponses5));

        List<Reponse> reponses6 = new ArrayList<>();
        reponses6.add(new Reponse(1, "Deux atomes partagent des électrons.", true));
        reponses6.add(new Reponse(2, "Deux atomes échangent des électrons.", false));
        reponses6.add(new Reponse(3, "Deux atomes partagent des neutrons.", false));
        reponses6.add(new Reponse(4, "Deux atomes échangent des protons.", false));
        questions.add(new Question(6, "Une liaison covalente se forme lorsque :", reponses6));

        List<Reponse> reponses7 = new ArrayList<>();
        reponses7.add(new Reponse(1, "6,02 × 10²³ molécules.", true));
        reponses7.add(new Reponse(2, "6,02 × 10²³ grammes.", false));
        reponses7.add(new Reponse(3, "1,66 × 10⁻²⁷ atomes.", false));
        reponses7.add(new Reponse(4, "Une masse de 1 gramme.", false));
        questions.add(new Question(7, "Une mole de n’importe quelle substance contient :", reponses7));

        List<Reponse> reponses8 = new ArrayList<>();
        reponses8.add(new Reponse(1, "Aucune modification n'est nécessaire.", false));
        reponses8.add(new Reponse(2, "Il faut ajouter un coefficient 2 devant H₂O.", false));
        reponses8.add(new Reponse(3, "Il faut ajouter un coefficient 2 devant H₂.", true));
        reponses8.add(new Reponse(4, "Il faut ajouter un coefficient 2 devant H₂ et H₂O.", false));
        questions.add(new Question(8, "Pour équilibrer l'équation suivante : H2 + O2 -> H2O", reponses8));

        List<Reponse> reponses9 = new ArrayList<>();
        reponses9.add(new Reponse(1, "Une solution dans laquelle le solvant est l’eau.", true));
        reponses9.add(new Reponse(2, "Une solution contenant uniquement des ions.", false));
        reponses9.add(new Reponse(3, "Une solution qui ne contient pas de soluté.", false));
        reponses9.add(new Reponse(4, "Une solution qui change toujours de couleur.", false));
        questions.add(new Question(9, "Une solution aqueuse est :", reponses9));

        List<Reponse> reponses10 = new ArrayList<>();
        reponses10.add(new Reponse(1, "Libère des ions OH⁻.", false));
        reponses10.add(new Reponse(2, "Libère des ions H⁺.", true));
        reponses10.add(new Reponse(3, "Libère des molécules d'eau.", false));
        reponses10.add(new Reponse(4, "Absorbe des électrons.", false));
        questions.add(new Question(10, "Un acide en solution aqueuse :", reponses10));

        List<Reponse> reponses11 = new ArrayList<>();
        reponses11.add(new Reponse(1, "Neutre.", false));
        reponses11.add(new Reponse(2, "Faiblement acide.", true));
        reponses11.add(new Reponse(3, "Fortement acide.", false));
        reponses11.add(new Reponse(4, "Basique.", false));
        questions.add(new Question(11, "Si une solution a un pH de 3, elle est :", reponses11));
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