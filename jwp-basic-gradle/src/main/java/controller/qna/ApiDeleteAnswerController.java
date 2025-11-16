package controller.qna;

import controller.Controller;
import dao.AnswerDao;
import model.Answer;
import model.Result;
import model.User;
import util.SessionUserUtils;
import view.JsonView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteAnswerController implements Controller {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!SessionUserUtils.isLoggedIn(req.getSession())){
            return new ModelAndView(new JsonView()).addObject("message", "로그인이 필요한 서비스입니다.");
        }

        long answerId = Long.parseLong(req.getParameter("answerId"));

        AnswerDao answerDao = new AnswerDao();
        Answer answer = answerDao.findById(answerId);

        User user = SessionUserUtils.getUserFromSession(req.getSession());

        if(user.isSameUser(answer.getWriter())){
            answerDao.delete(answerId);
            return new ModelAndView(new JsonView()).addObject("result", Result.ok()) ;
        }

        return new ModelAndView(new JsonView()).addObject("message", "해당 댓글 작성자만 삭제할 수 있습니다.");
    }
}
