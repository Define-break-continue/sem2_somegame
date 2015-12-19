package ru.bagrusss.servces.database.executor;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.List;

/**
 * Created by vladislav
 */

public class Executor {


    public <T> T runTypedQuery(@NotNull Connection connection, String sql, TResultHandler<T> tHandler) throws SQLException {
        T res = null;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            res = tHandler.handle(resultSet);
        } finally {
            connection.close();
        }
        return res;
    }


    public long runUpdate(@NotNull Connection connection, String sql) throws SQLException {
        long res = 0L;
        try (Statement statement = connection.createStatement()) {
            res = statement.executeUpdate(sql);
        } finally {
            connection.close();
        }
        return res;
    }

    public <T> T runUpdate(@NotNull Connection connection, String sql, TResultHandler<T> handler) throws SQLException {
        T res = null;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (handler != null)
                    res = handler.handle(resultSet);
            }
        } finally {
            connection.close();
        }
        return res;
    }

    public <T> T runTypedPreparedQuery(@NotNull Connection connection, String sql, List<?> params, TResultHandler<T> handler) throws SQLException {
        T res = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object par : params) {
                preparedStatement.setObject(i++, par);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                res = handler.handle(resultSet);
            }
        } finally {
            connection.close();
        }
        return res;
    }


}
