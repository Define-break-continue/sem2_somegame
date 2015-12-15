package ru.bagrusss.servces.database;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.servces.BaseInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vladislav
 */

public interface DataBaseService extends BaseInterface{

    Connection getConnection() throws SQLException;

    void runQuery(@NotNull Connection connection, String sql, ResultHandler handler) throws SQLException;

    <T> T runTypedQuery(@NotNull Connection connection, String sql, TResultHandler<T> tHandler) throws SQLException;

    void runPreparedQuery(@NotNull Connection connection, String sql,
                          List<?> params, ResultHandler handler) throws SQLException;

    <T> T runTypedPreparedQuery(@NotNull Connection connection, String sql,
                                List<?> params, TResultHandler<T> rs) throws SQLException;

    int runUpdate(@NotNull Connection connection, String sql) throws SQLException;

    int runPreparedUpdate(@NotNull Connection connection, String sql, List<?> params) throws SQLException;

}
