package controller.qna;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.QnaService;
import util.SessionUserUtils;
import view.JspView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);

    private final QnaService qnaService = new QnaService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!SessionUserUtils.isLoggedIn(req.getSession())) {
            log.error("로그인이 필요한 서비스입니다.");

            return new ModelAndView(new JspView("/user/login.jsp"));
        }

        Long questionId = Long.parseLong(req.getParameter("questionId"));

        if(!qnaService.canDeleteQuestion(questionId)){
            return new ModelAndView(new JspView("redirect:/qna/show?questionId=" + questionId));
        }

        qnaService.deleteQuestion(questionId);

        return new ModelAndView(new JspView("/"));
    }
}
