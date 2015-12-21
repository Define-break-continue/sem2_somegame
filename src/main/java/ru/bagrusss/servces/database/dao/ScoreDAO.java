package ru.bagrusss.servces.database.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;
import ru.bagrusss.servces.database.executor.Executor;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vladislav
 */

@SuppressWarnings({"InstanceVariableNamingConvention",
        "StringBufferReplaceableByString"})
public class ScoreDAO {

    private final Executor mExecutor;
    public static final String TABLE_STATISTICS = " `Score` ";

    public ScoreDAO(@NotNull Executor mExecutor) {
        this.mExecutor = mExecutor;
    }

    @SuppressWarnings({"CanBeFinal", "InnerClassMayBeStatic"})
    public static class Score {

        private boolean isWin;
        private long score;
        private long id;

        @SuppressWarnings("unused")
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

    public void createScoreTable() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_STATISTICS)
                .append("(`user_id` INT, ")
                .append("`games` INT UNSIGNED DEFAULT 0, ")
                .append("`wins` INT UNSIGNED DEFAULT 0, ")
                .append("`score` INT UNSIGNED DEFAULT 0, ")
                .append("PRIMARY KEY (`user_id`), ")
                .append("INDEX idx_score (`score`)) ")
                .append("DEFAULT CHARACTER SET = utf8");
        mExecutor.runUpdate(sql.toString());
    }

    public ScoreDataSet getUserScore(long id) throws SQLException {
        ScoreDataSet empty = new ScoreDataSet(id, 0, 0, 0);
        try {
            new UserDAO(mExecutor).getUserById(id);
        } catch (SQLException e) {
            return empty;
        }
        StringBuilder sql = new StringBuilder()
                .append("SELECT * FROM")
                .append(TABLE_STATISTICS).append("WHERE ")
                .append("`user_id`=").append(id);
        return mExecutor.runTypedQuery(sql.toString(), rs -> {
            if (rs.next())
                return new ScoreDataSet(id, rs.getLong(2), rs.getLong(3), rs.getLong(4));
            else {
                insertUserScore(id);
                return empty;
            }
        });
    }

    public boolean updateUserScore(long id, boolean isWin, long score) throws SQLException {
        if (id <= 0 || score < 0)
            return false;
        StringBuilder sql = new StringBuilder("UPDATE").append(TABLE_STATISTICS)
                .append("SET `wins`=`wins`+").append(isWin)
                .append(", `games`=`games`+1, `score`=`score`+").append(score)
                .append(" WHERE `user_id`=").append(id);
        return mExecutor.runUpdate(sql.toString()) > 0;
    }


    private void insertUserScore(long id) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO")
                .append(TABLE_STATISTICS).append("(`user_id`) ")
                .append("VALUES (").append(id).append(')');
        mExecutor.runUpdate(sql.toString());
    }

    public boolean saveResults(List<Score> scores) throws SQLException {
        for (Score sc : scores) {
            if (!updateUserScore(sc.getId(), sc.isWin(), sc.getScore()))
                return false;
        }
        return true;
    }

    @Nullable
    public List<ScoreDataSet> getBest(long count) throws SQLException {
        if (count < 1)
            return null;
        StringBuilder sql = new StringBuilder("SELECT * FROM")
                .append(TABLE_STATISTICS).append("ORDER BY `score` LIMIT ")
                .append(count);
        return mExecutor.runTypedQuery(sql.toString(), rs -> {
            List<ScoreDataSet> res = new LinkedList<>();
            while (rs.next()) {
                res.add(new ScoreDataSet(rs.getLong(1), rs.getLong(2),
                        rs.getLong(3), rs.getLong(4)));
            }
            return res;
        });
    }

}
