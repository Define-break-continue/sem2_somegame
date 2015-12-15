package ru.bagrusss.servces.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.List;

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

    public static final String TABLE_USER = " `User` ";
    public static final String TABLE_STATISTICS = " `Statistics` ";
    public static final String TABLE_ADMINS = " `Admin` ";

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

    public boolean checkDataBase() {
        try {
            mDBHelper.runUpdate(mDBHelper.getConnection(), "SHOW TABLES");
            return true;
        } catch (SQLException e) {
            return false;
        }
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

    private void createDataBase() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_USER)
                .append("(`id` INT NOT NULL AUTO_INCREMENT, ")
                .append("`email` VARCHAR(50), ")
                .append("`first_name` VARCHAR(35), ")
                .append("`last_name` VARCHAR(35), ")
                .append("PRIMARY KEY (`id`)) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        mDBHelper.runUpdate(getConnection(), sql.toString());
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_STATISTICS)
                .append("(`user_id` INT, ")
                .append("games_count INT UNSIGNED DEFAULT 0, ")
                .append("games_wins INT UNSIGNED DEFAULT 0) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        mDBHelper.runUpdate(getConnection(), sql.toString());
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_ADMINS)
                .append("(`admin_id` INT NOT NULL, ")
                .append("(`isActivated` TINYINT(0) DEFAULT 0, ")
                .append("PRIMARY KEY (`admin_id`)) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        mDBHelper.runUpdate(getConnection(), sql.toString());

    }

    @Override
    public Connection getConnection() throws SQLException {
        return mBasicDataSource.getConnection();
    }

    @Override
    public void runQuery(@NotNull Connection connection, String sql, ResultHandler resultHandler) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultHandler.handle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
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

    @Override
    public void runPreparedQuery(@NotNull Connection connection, String sql, List<?> params,
                                 ResultHandler handler) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object par : params) {
                preparedStatement.setObject(i++, par);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                handler.handle(resultSet);
            }
        } finally {
            connection.close();
        }
    }


    @Override
    public <T> T runTypedPreparedQuery(@NotNull Connection connection, String sql, List<?> params,
                                       TResultHandler<T> tHandler) throws SQLException {
        T res = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object par : params) {
                preparedStatement.setObject(i++, par);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                res = tHandler.handle(resultSet);
            }
        } finally {
            connection.close();
        }
        return res;
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
    public int runPreparedUpdate(@NotNull Connection connection, String sql, List<?> params) throws SQLException {
        return 0;
    }

}
