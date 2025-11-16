package controller.qna;

import controller.Controller;
import dao.QuestionDao;
import model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SessionUserUtils;
import view.JspView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!SessionUserUtils.isLoggedIn(req.getSession())) {
            log.error("로그인이 필요한 서비스입니다.");

            return new ModelAndView(new JspView("/user/login.jsp"));
        }

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
