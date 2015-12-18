package ru.bagrusss.servces.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.main.Main;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */

public final class ServiceDB implements DataBaseService {

    private final BasicDataSource mBasicDataSource;

    public static final String TABLE_USER = " `User` ";
    public static final String TABLE_STATISTICS = " `Statistics` ";
    public static final String TABLE_ADMINS = " `Admin` ";
    public static final String DB_CONFIGS = Main.RESOURCE_PATH + "//.cfg//db.json";

    @SuppressWarnings("ALL")
    private class ConfigsDB {
        private String pref;
        private String driver;
        private String datadase;
        private String host;
        private String port;
        private String base;
        private String params;
        private int maxPreparedStatements;
        private int minConnections;
        private int maxConnections;
        private String user;
        private String password;
    }

    private final Logger dbLogger = Logger.getLogger(this.getClass().getCanonicalName());

    @NotNull
    private String buildURL(@NotNull ConfigsDB cfg) {
        StringBuilder url = new StringBuilder();
        url.append(cfg.pref).append(':')
                .append(cfg.datadase).append("://")
                .append(cfg.host).append(':')
                .append(cfg.port).append('/')
                .append(cfg.base).append('?')
                .append(cfg.params);
        return url.toString();
    }

    public ServiceDB() {
        ConfigsDB conf = null;
        try {
            conf = Resourses.readResourses(DB_CONFIGS, ConfigsDB.class);
        } catch (IOException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
            System.exit(Main.DB_CONFIGS_ERROR);
        }
        mBasicDataSource = new BasicDataSource();
        mBasicDataSource.setDriverClassName(conf.driver);
        mBasicDataSource.setUsername(conf.user);
        mBasicDataSource.setPassword(conf.password);
        mBasicDataSource.setUrl(buildURL(conf));

        mBasicDataSource.setMinIdle(conf.minConnections);
        mBasicDataSource.setMaxIdle(conf.maxConnections);
        mBasicDataSource.setMaxOpenPreparedStatements(conf.maxPreparedStatements);
        try {
            createDataBase();
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
            System.exit(Main.DB_ERROR);
        }
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
        runUpdate(getConnection(), sql.toString());
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_STATISTICS)
                .append("(`user_id` INT, ")
                .append("games_count INT UNSIGNED DEFAULT 0, ")
                .append("games_wins INT UNSIGNED DEFAULT 0, ")
                .append("score INT UNSIGNED DEFAULT 0) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        runUpdate(getConnection(), sql.toString());
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_ADMINS)
                .append("(`email` VARCHAR(50) NOT NULL, ")
                .append("`isActivated` TINYINT(0) DEFAULT 0, ")
                .append("PRIMARY KEY (`email`)) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        runUpdate(getConnection(), sql.toString());
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
        int res = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object par : params) {
                preparedStatement.setObject(i++, par);
            }
            res = preparedStatement.executeUpdate();
        } finally {
            connection.close();
        }
        return res;
    }

}
