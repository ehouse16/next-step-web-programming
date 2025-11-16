package controller.qna;

import controller.Controller;
import model.Result;
import service.QnaService;
import util.SessionUserUtils;
import view.JsonView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiQuestionDeleteController implements Controller {
    private final QnaService qnaService = new QnaService();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!SessionUserUtils.isLoggedIn(req.getSession())) {
            return new ModelAndView(new JsonView()).addObject("message", "로그인이 필요한 서비스입니다.");
        }

        Long questionId = Long.parseLong(req.getParameter("questionId"));

        if(!qnaService.canDeleteQuestion(questionId)){
            return new ModelAndView(new JsonView()).addObject("result", Result.fail("답변자와 질문자가 다르기에 질문을 삭제할 수 없습니다."));
        }

        qnaService.deleteQuestion(questionId);

        return new ModelAndView(new JsonView()).addObject("result", Result.ok());

    }
}
