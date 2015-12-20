package ru.bagrusss.servces.database.executor;

import org.jetbrains.annotations.Nullable;
import ru.bagrusss.helpers.BaseInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vladislav
 */
public interface TResultHandler<T> extends BaseInterface {
    @Nullable
    T handle(ResultSet rs) throws SQLException;
}
