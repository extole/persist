package net.sf.persist.mapping;

import java.lang.reflect.Method;

import net.sf.persist.PersistException;

public class DefaultPersistAttributeGetter implements PersistAttributeGetter {
    @Override
    public Object getValue(Object instance, Method getter) {
        try {
            Object value = getter.invoke(instance);
            return convertForPersistentLayer(value);
        } catch (Exception e) {
            String message = String.format("Error getting value from instance [%s] using getter[%s]", instance, getter);
            throw new PersistException(message, e);
        }
    }

    protected Object convertForPersistentLayer(Object instance) {
        return instance;
    }
}