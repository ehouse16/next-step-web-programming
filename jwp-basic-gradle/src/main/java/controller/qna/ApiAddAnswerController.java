package controller.qna;

import controller.Controller;
import dao.AnswerDao;
import dao.QuestionDao;
import model.Answer;
import model.Result;
import model.User;
import util.SessionUserUtils;
import view.JsonView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiAddAnswerController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!SessionUserUtils.isLoggedIn(req.getSession())) {
            return new ModelAndView(new JsonView()).addObject("result", Result.fail("Login is required"));
        }

        User user = SessionUserUtils.getUserFromSession(req.getSession());
        Answer answer = new Answer(
                user.getUserId(),
                req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId"))
        );

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);

        QuestionDao questionDao = new QuestionDao();
        questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());

        return new ModelAndView(new JsonView()).addObject("answer", savedAnswer).addObject("result", Result.ok());
    }
}
