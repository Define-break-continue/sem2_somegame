package ru.bagrusss.servces.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by vladislav
 */

public interface DataBaseService {

    Connection getConnection() throws SQLException;

    void runQuery(@NotNull Connection connection, String sql, ResultHandlet resultHandlet) throws SQLException;

    void runPreparedQuery(@NotNull Connection connection, String sql, Map<Integer, Object> params, ResultHandlet resultHandlet) throws SQLException;

    int runUpdate(@NotNull Connection connection, String sql) throws SQLException;

    void runPreparedUpdate(@NotNull Connection connection, String sql, Map<Integer, String> params) throws SQLException;
}
