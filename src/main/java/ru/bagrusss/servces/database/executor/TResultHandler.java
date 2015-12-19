package ru.bagrusss.servces.database.executor;

import ru.bagrusss.helpers.BaseInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vladislav
 */
public interface TResultHandler<T> extends BaseInterface {
    T handle(ResultSet rs) throws SQLException;
}
