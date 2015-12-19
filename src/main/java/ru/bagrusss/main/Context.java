package ru.bagrusss.main;

import ru.bagrusss.helpers.BaseInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislav on 15.12.15.
 */

public class Context {

    private Map<Class<? extends BaseInterface>, Object> services = new HashMap<>();

    public void add(Class<? extends BaseInterface> cl, Object implementor) throws Exception {
        if (services.containsKey(cl)) {
            throw new Exception("Service " + cl.getName() + " already exists!");
        }
        services.put(cl, implementor);
    }

    public Object get(Class<? extends BaseInterface> key) {
        return services.get(key);
    }
}
