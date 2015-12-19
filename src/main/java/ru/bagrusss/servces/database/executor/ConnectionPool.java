package ru.bagrusss.servces.database.executor;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.main.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */

@SuppressWarnings("all")
/**
 * поскольку idea предлагает заменить StringBuilder на конкатенацию String
 */

public final class ConnectionPool {

    private static volatile ConnectionPool sInstance;
    private final BasicDataSource mBasicDataSource;
    public static final String DB_CONFIGS = Main.RESOURCE_PATH + "//.cfg//db.json";
    private final Logger dbLogger = Logger.getLogger(getClass().getCanonicalName());

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

    private ConnectionPool() {
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
    }

    static ConnectionPool getInstance() {
        ConnectionPool local = sInstance;
        if (local == null) {
            synchronized (ConnectionPool.class) {
                local = sInstance;
                if (local == null) {
                    sInstance = local = new ConnectionPool();
                }
            }
        }
        return sInstance;
    }

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

    Connection getConnection() throws SQLException {
        return mBasicDataSource.getConnection();
    }
}
