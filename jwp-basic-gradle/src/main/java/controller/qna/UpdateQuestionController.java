package controller.qna;

import controller.Controller;
import dao.QuestionDao;
import model.Question;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SessionUserUtils;
import view.JspView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User value = SessionUserUtils.getUserFromSession(req.getSession());

        if(value == null){
            log.error("로그인한 회원만 수정을 할 수 있습니다.");

            return new ModelAndView(new JspView("redirect:/user/login.jsp"));
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);

        if(!question.isSameUser(value)){
            log.error("다른 사용자가 쓴 글을 수정할 수 없습니다.");

            return new ModelAndView(new JspView("/"));
        }

        Question newQuestion = new Question(question.getWriter(), req.getParameter("title"), req.getParameter("contents"));
        question.update(newQuestion);
        questionDao.update(question);

        return new ModelAndView(new JspView("redirect:/"));
    }
}
