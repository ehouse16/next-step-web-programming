package controller.qna;

import controller.Controller;
import dao.AnswerDao;
import view.JsonView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteAnswerController implements Controller {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));

        AnswerDao answerDao = new AnswerDao();
        answerDao.delete(answerId);

        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("success", true);

        return mav;
    }
}
