package ru.bagrusss.servces.account;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.dao.AdminDAO;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dao.UserDAO;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */

public final class ServiceDB implements AccountService {

    private final BasicDataSource mBasicDataSource;
    public static final String DB_CONFIGS = Main.RESOURCE_PATH + "//.cfg//db.json";
    private UserDAO mUserDAO;
    private AdminDAO mAdminDAO;
    private ScoreDAO mScoreDAO;

    private Map<String, UserDataSet> mSessions = new HashMap<>();

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

    private final Logger dbLogger = Logger.getLogger(getClass().getCanonicalName());

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
            mUserDAO = new UserDAO(getConnection());
            mAdminDAO = new AdminDAO(getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {
        dbLogger.log(Level.INFO, "Active connections " + mBasicDataSource.getNumActive() + '\n');
        return mBasicDataSource.getConnection();
    }


    /**
     * used for test only!
     */
    @Deprecated
    @Override
    public void removeAll() {

    }

    @Override
    public boolean isAdmin(String email) {
        try {
            return mAdminDAO.isAdmin(getConnection(), email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long registerUser(@NotNull String email, @NotNull UserDataSet user) {
        try {
            return mUserDAO.insertUser(getConnection(), user);
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean addSession(@NotNull String sessionId, @NotNull UserDataSet user) {
        return mSessions.put(sessionId, user) == null;
    }

    @Override
    public boolean removeSession(@NotNull String sessionId) {
        return mSessions.remove(sessionId) != null;
    }

    @Nullable
    @Override
    public UserDataSet getUser(@NotNull String email) {
        return null;
    }

    @Nullable
    @Override
    public UserDataSet getUser(@NotNull String email, @NotNull String password) {
        try {
            return mUserDAO.getUser(getConnection(), email, password);
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    @Nullable
    @Override
    public UserDataSet getSession(@NotNull String sessionId) {
        return mSessions.get(sessionId);
    }

    @Override
    public int getCountActivatedUsers() {
        return mSessions.size();
    }

    @Override
    public long getCountRegisteredUsers() {
        try {
            return mUserDAO.getUserCount(getConnection());
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    /*
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_STATISTICS)
                .append("(`user_id` INT, ")
                .append("games_count INT UNSIGNED DEFAULT 0, ")
                .append("games_wins INT UNSIGNED DEFAULT 0, ")
                .append("score INT UNSIGNED DEFAULT 0) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        runUpdate(getConnection(), sql.toString(), null);
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_ADMINS)
                .append("(`email` VARCHAR(50) NOT NULL, ")
                .append("`isActivated` TINYINT(0) DEFAULT 0, ")
                .append("PRIMARY KEY (`email`)) ")
                .append("DEFAULT CHARACTER SET = utf8, ENGINE = InnoDB");
        runUpdate(getConnection(), sql.toString(), null);
        }*/

}
