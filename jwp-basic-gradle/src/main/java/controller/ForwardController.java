package controller;

import dao.QuestionDao;
import dao.UserDao;
import model.Question;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SessionUserUtils;
import view.JspView;
import view.ModelAndView;
import view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ForwardController implements Controller {
    private final Logger log = LoggerFactory.getLogger(ForwardController.class);

    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        if(forwardUrl.equals("/user/updateForm.jsp")) {
            Object user = req.getSession().getAttribute("user");

            if(user != null) {
                String userId = req.getParameter("userId");
                UserDao userDao = new UserDao();
                User currentUser = userDao.findByUserId(userId);

                req.setAttribute("user", currentUser);
            }
        } else if(forwardUrl.equals("/qna/form.jsp")){
            if(SessionUserUtils.isLoggedIn(req.getSession())) {
                req.setAttribute("writer", req.getSession().getAttribute("user"));

                return new ModelAndView(new JspView("/qna/form.jsp"));
            }
            log.debug("로그인 한 회원만 질문을 남길 수 있습니다.");
            return new ModelAndView(new JspView("/user/login.jsp"));
        }
        else if(forwardUrl.equals("/qna/updateForm.jsp")) {
            QuestionDao questionDao = new QuestionDao();
            Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));

            Object session = req.getSession().getAttribute("user");

            if(session == null) {
                log.error("로그인 한 회원만 수정이 가능합니다.");

                return new ModelAndView(new JspView("/user/login.jsp"));
            } else {
                User user = SessionUserUtils.getUserFromSession(req.getSession());

                if (question.isSameUser(user)) {
                    req.setAttribute("title", question.getTitle());
                    req.setAttribute("content", question.getContents());
                }

                //todo: 이런거는 alert로 해야하는 거 아닌가,, 바로 리다이렉트보다는,,
                log.error("해당 질문을 한 작성자만 수정이 가능합니다.");
                return new ModelAndView(new JspView("/"));
            }
        }
        return new ModelAndView(new JspView(forwardUrl));
    }
}
