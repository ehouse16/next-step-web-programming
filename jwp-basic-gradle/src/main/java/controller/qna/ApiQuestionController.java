package controller.qna;

import controller.Controller;
import dao.QuestionDao;
import view.JsonView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiQuestionController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();

        return new ModelAndView(new JsonView()).addObject("questions", questionDao.findAll());
    }
}
