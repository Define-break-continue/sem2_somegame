package ru.bagrusss.main;

import ru.bagrusss.helpers.BaseInterface;
import ru.bagrusss.helpers.InitException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislav on 15.12.15.
 */

public class Context {

    private final Map<Class<? extends BaseInterface>, Object> services = new HashMap<>();

    public void add(Class<? extends BaseInterface> cl, Object implementor) throws InitException {
        if (services.containsKey(cl)) {
            throw new InitException("Service " + cl.getName() + " already exists!");
        }
        services.put(cl, implementor);
    }

    public Object get(Class<? extends BaseInterface> key) {
        return services.get(key);
    }

    public void clear(){
        services.clear();
    }
}
