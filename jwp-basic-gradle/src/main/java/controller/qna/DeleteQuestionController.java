package controller.qna;

import controller.Controller;
import dao.AnswerDao;
import dao.QuestionDao;
import model.Answer;
import model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.JspView;
import view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteQuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));
        if(question.getCountOfAnswer() > 0){
            List<Answer> answers = answerDao.findAllByQuestionId(question.getQuestionId());

            boolean isDifferent = answers.stream().anyMatch(answer -> !answer.getWriter().equals(question.getWriter()));

            if(isDifferent){
                log.error("답변자와 질문자가 다르기에 질문을 삭제할 수 없습니다.");

                return new ModelAndView(new JspView("redirect:/qna/show?questionId="+question.getQuestionId()));
            }
        }

        questionDao.delete(question.getQuestionId());

        return new ModelAndView(new JspView("/"));
    }
}
