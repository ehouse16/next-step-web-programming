package controller;

import dao.UserDao;
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
        return new ModelAndView(new JspView(forwardUrl));
    }
}
