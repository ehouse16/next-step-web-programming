package dao;

import model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionDao {
    private static final Logger log = LoggerFactory.getLogger(QuestionDao.class);

    private static final String SELECTQUERY = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId = ?";
    private static final String SELECTALLQUERY = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
            + "order by questionId desc";
    private static final String INSERTQUERY = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
    private static final String QUESTIONID = "questionId";
    private static final String WRITER = "writer";
    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";
    private static final String CREATEDDATE = "createdDate";
    private static final String COUNTOFANSWER = "countOfAnswer";

    public List<Question> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        RowMapper<Question> rm = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                null,
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer")
        );

        return jdbcTemplate.query(SELECTALLQUERY, rm);
    }

    public Question findById(Long questionId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        RowMapper<Question> rm = rs -> new Question(
                rs.getLong(QUESTIONID),
                rs.getString(WRITER),
                rs.getString(TITLE),
                rs.getString(CONTENTS),
                rs.getTimestamp(CREATEDDATE),
                rs.getInt(COUNTOFANSWER)
        );

        return jdbcTemplate.queryForObject(SELECTQUERY, rm, questionId);
    }

    public void insert(Question question) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = ps -> {
            ps.setString(1, question.getWriter());
            ps.setString(2, question.getTitle());
            ps.setString(3, question.getContents());
            ps.setTimestamp(4, new Timestamp(question.getCreatedDate().getTime()));
            ps.setInt(5, question.getCountOfAnswer());
        };

        jdbcTemplate.update(INSERTQUERY, pss);
    }
}
