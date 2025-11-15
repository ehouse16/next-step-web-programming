package controller.qna;

import controller.Controller;
import service.QnaService;
import view.JspView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController implements Controller {
    private final QnaService qnaService = new QnaService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        if(!qnaService.canDeleteQuestion(questionId)){
            return new ModelAndView(new JspView("redirect:/qna/show?questionId=" + questionId));
        }

        qnaService.deleteQuestion(questionId);

        return new ModelAndView(new JspView("/"));
    }
}
