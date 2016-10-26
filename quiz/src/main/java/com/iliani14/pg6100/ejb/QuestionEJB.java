package com.iliani14.pg6100.ejb;

import com.iliani14.pg6100.entity.Question;
import com.iliani14.pg6100.entity.SubSubCategory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by anitailieva on 26/10/2016.
 */

@Stateless
public class QuestionEJB {


    @PersistenceContext
    private EntityManager em;

    @EJB
    private SubSubCategoryEJB subSubCategoryEJB;

    public Question createQuiz(SubSubCategory s, String text, List<String> answers, String theCorrectAnswer) {
        Question q = new Question();
        q.setText(text);
        q.setAnswers(answers);
        q.setTheCorrectAnswer(theCorrectAnswer);

        em.persist(q);

        SubSubCategory subsub = subSubCategoryEJB.findSubSubCategoryById(s.getId());
        subsub.getQuestions().add(q);


        return q;
    }

    public Question findQuizById(long id) {
        return em.find(Question.class, id);
    }

    public List<Question> getAllQuizes() {
        Query query = em.createNamedQuery(Question.GET_ALL_QUESTIONS);
        List<Question> quizes = query.getResultList();

        return quizes;
    }

    public Question getQuizBySubSubCategoryName(String subSubCategoryName) {
        Query query = em.createNamedQuery(Question.GET_QUESTION_BY_SUBSUBCATEGORY);
        query.setParameter("subSubCategoryName", subSubCategoryName);

        return (Question) query.getResultList();
    }

    public void deleteQuestion(long id) {
        Question quiz = em.find(Question.class, id);
        if (quiz != null) {
            em.remove(quiz);
        }
    }

    public void updateQuestion(long id, String newName) {
         Question question= em.find(Question.class, id);
        if (question != null) {
            question.setText(newName);

        }
    }

}