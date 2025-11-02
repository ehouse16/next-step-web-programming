package controller.qna;

import controller.Controller;
import dao.QuestionDao;
import model.Question;
import view.JspView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Question question = new Question(
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents")
        );

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);

        return new ModelAndView(new JspView("redirect:/"));
    }
}
