package ru.bagrusss.servces.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Map;

/**
 * Created by vladislav
 */

public final class DataBaseServiceImpl implements DataBaseService {

    private static final String DB_HOST = "jdbc:mysql://localhost:3306/java_2015_09_g07_db";
    private static final String DB_USER = "java_2015_09_g07";
    private static final String DB_PASS = "java_2015_09_g07";
    private static final int MAX_OPEN_PREPARED_STATEMENTS = 100;
    private static DataBaseServiceImpl mDBHelper;
    private final BasicDataSource mBasicDataSource;

    private DataBaseServiceImpl() {
        mBasicDataSource = new BasicDataSource();
        mBasicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        mBasicDataSource.setUsername(DB_USER);
        mBasicDataSource.setPassword(DB_PASS);
        mBasicDataSource.setUrl(DB_HOST);

        mBasicDataSource.setMinIdle(4);
        mBasicDataSource.setMaxIdle(10);
        mBasicDataSource.setMaxOpenPreparedStatements(MAX_OPEN_PREPARED_STATEMENTS);
    }

    public static DataBaseServiceImpl getInstance() {
        DataBaseServiceImpl localInstance = mDBHelper;
        if (localInstance == null) {
            synchronized (DataBaseServiceImpl.class) {
                localInstance = mDBHelper;
                if (localInstance == null)
                    mDBHelper = localInstance = new DataBaseServiceImpl();
            }
        }
        return localInstance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return mBasicDataSource.getConnection();
    }

    @Override
    public void runQuery(@NotNull Connection connection, String sql, ResultHandlet resultHandlet) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultHandlet.handle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void runPreparedQuery(@NotNull Connection connection, String sql,
                                 Map<Integer, Object> params, ResultHandlet result) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Integer i : params.keySet()) {
                preparedStatement.setObject(i, params.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            result.handle(resultSet);
            resultSet.close();
        } finally {
            connection.close();
        }
    }

    @Override
    public int runUpdate(@NotNull Connection connection, String sql) throws SQLException {
        int updated = 0;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            updated = statement.getUpdateCount();
        } finally {
            connection.close();
        }
        return updated;
    }

    @Override
    public void runPreparedUpdate(@NotNull Connection connection, String sql, Map<Integer, String> params) throws SQLException {

    }
}
