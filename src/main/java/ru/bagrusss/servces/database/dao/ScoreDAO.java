package ru.bagrusss.servces.database.dao;

import org.jetbrains.annotations.Nullable;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;
import ru.bagrusss.servces.database.executor.Executor;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by vladislav
 */
@SuppressWarnings("all")
public class ScoreDAO {

    private Executor mExecutor = new Executor();
    public static final String TABLE_STATISTICS = " `Statistics` ";

    public class Score {

        private boolean isWin;
        private long score;
        private long id;

        public Score(long id, boolean isWin, long score) {
            this.isWin = isWin;
            this.score = score;
            this.id = id;
        }

        public boolean isWin() {
            return isWin;
        }

        public long getScore() {
            return score;
        }


        public long getId() {
            return id;
        }
    }

    public ScoreDAO() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_STATISTICS)
                .append("(`user_id` INT, ")
                .append("`games` INT UNSIGNED DEFAULT 0, ")
                .append("`wins` INT UNSIGNED DEFAULT 0, ")
                .append("`score` INT UNSIGNED DEFAULT 0, ")
                .append("PRIMARY KEY (`user_id`) ")
                .append("DEFAULT CHARACTER SET = utf8");
        mExecutor.runUpdate(sql.toString());
    }

    @Nullable
    public ScoreDataSet getUserScore(long id) throws SQLException {
        if (new UserDAO().getUserById(id) == null)
            return null;
        StringBuilder sql = new StringBuilder()
                .append("SELECT *, `games`-`wins` AS `lose` FROM")
                .append(TABLE_STATISTICS).append("WHERE ")
                .append("`user_id`=").append(id);
        return mExecutor.runTypedQuery(sql.toString(),
                rs -> rs.next() ? new ScoreDataSet(id, rs.getLong(2),
                        rs.getLong(3), rs.getLong(4)) : createUserScore(id));
    }

    public boolean updateUserScore(long id, boolean isWin, long score) throws SQLException {
        if (id <= 0 || score < 0)
            return false;
        StringBuilder sql = new StringBuilder("UPDATE").append(TABLE_STATISTICS)
                .append("SET `wins`=`wins`+").append(isWin)
                .append(", `games`=`games`+1, `score`+").append(score)
                .append(" WHERE `user_id`=").append(id);
        return mExecutor.runUpdate(sql.toString()) > 0;
    }

    @Nullable
    private ScoreDataSet createUserScore(long id) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO")
                .append(TABLE_STATISTICS).append("(`user_id`) ")
                .append("VALUES (").append(id).append(')');
        return mExecutor.runUpdate(sql.toString(),
                rs -> rs.next() ? new ScoreDataSet(rs.getLong(1), 0, 0, 0) : null);
    }

    public boolean saveResults(List<Score> scores) throws SQLException {
        for (Score sc : scores) {
            if (!updateUserScore(sc.getId(), sc.isWin(), sc.getScore()))
                return false;
        }
        return true;
    }

}
