package service;

import dao.AnswerDao;
import dao.QuestionDao;
import model.Answer;
import model.Question;

import java.util.List;

public class QnaService {
    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    public boolean canDeleteQuestion(Long questionId) throws Exception{
        Question question = questionDao.findById(questionId);

        if(question.getCountOfAnswer() == 0){
            return true;
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        boolean isDifferent = answers.stream().anyMatch(answer -> !answer.getWriter().equals(question.getWriter()));

        return !isDifferent;
    }

    public void deleteQuestion(Long questionId) throws Exception{
        questionDao.delete(questionId);
    }

    public Question findQuestion(Long questionId) throws Exception{
        return questionDao.findById(questionId);
    }
}
