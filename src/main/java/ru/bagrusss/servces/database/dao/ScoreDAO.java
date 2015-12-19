package ru.bagrusss.servces.database.dao;

import org.jetbrains.annotations.Nullable;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;
import ru.bagrusss.servces.database.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vladislav
 */

public class ScoreDAO {

    private Executor mExecutor = new Executor();

    @Nullable
    public ScoreDataSet getUserScore(long id) {
        return null;
    }

    public boolean updateUserScore(Connection conn, long id, boolean isWin, long scores) throws SQLException {
        return true;
    }
}
