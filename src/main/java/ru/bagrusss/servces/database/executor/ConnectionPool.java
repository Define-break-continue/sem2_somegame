package ru.bagrusss.servces.database.executor;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.helpers.Resourses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */


@SuppressWarnings("StringBufferReplaceableByString")
public final class ConnectionPool {

    private static volatile ConnectionPool sInstance;
    private final BasicDataSource mBasicDataSource;
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getCanonicalName());

    public static final byte DB_CONFIGS_ERROR = 4;

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

    private ConnectionPool(String path) {
        ConfigsDB conf = null;
        try {
            conf = Resourses.readResourses(path, ConfigsDB.class);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            System.exit(DB_CONFIGS_ERROR);
        }
        mBasicDataSource = new BasicDataSource();
        mBasicDataSource.setDriverClassName(conf.driver);
        mBasicDataSource.setUsername(conf.user);
        mBasicDataSource.setPassword(conf.password);
        mBasicDataSource.setUrl(this.buildURL(conf));

        mBasicDataSource.setMinIdle(conf.minConnections);
        mBasicDataSource.setMaxIdle(conf.maxConnections);
        mBasicDataSource.setMaxOpenPreparedStatements(conf.maxPreparedStatements);
        LOG.log(Level.INFO, "Connection Pool initialized from " + path);
    }

    static ConnectionPool getInstance(String configpath) {
        ConnectionPool local = sInstance;
        if (local == null) {
            synchronized (ConnectionPool.class) {
                local = sInstance;
                if (local == null) {
                    sInstance = new ConnectionPool(configpath);
                }
            }
        }
        return sInstance;
    }

    @NotNull
    private String buildURL(@NotNull ConfigsDB cfg) {
        StringBuilder url = new StringBuilder()
                .append(cfg.pref).append(':')
                .append(cfg.datadase).append("://")
                .append(cfg.host).append(':')
                .append(cfg.port).append('/')
                .append(cfg.base).append('?')
                .append(cfg.params);
        String urll = url.toString();
        LOG.log(Level.INFO, urll);
        return urll;
    }

    Connection getConnection() throws SQLException {
        return mBasicDataSource.getConnection();
    }
}
