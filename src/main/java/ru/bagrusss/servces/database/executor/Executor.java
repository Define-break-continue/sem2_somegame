package ru.bagrusss.servces.database.executor;

import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.List;

/**
 * Created by vladislav
 */

public class Executor {

    private final ConnectionPool mConnectionPool = ConnectionPool.getInstance();

    public <T> T runTypedQuery(String sql, TResultHandler<T> tHandler) throws SQLException {
        try (Connection connection = mConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return tHandler.handle(resultSet);
        }
    }

    public long runUpdate(String sql) throws SQLException {
        try (Connection connection = mConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    @Nullable
    public <T> T runUpdate(String sql, TResultHandler<T> handler) throws SQLException {
        try (Connection connection = mConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (handler != null)
                    return handler.handle(resultSet);
            }
        }
        return null;
    }

    public <T> T runTypedPreparedQuery(String sql, List<?> params, TResultHandler<T> handler) throws SQLException {
        try (Connection connection = mConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object par : params) {
                preparedStatement.setObject(i++, par);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return handler.handle(resultSet);
            }
        }
    }


}
