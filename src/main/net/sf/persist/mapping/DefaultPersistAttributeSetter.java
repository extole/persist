package net.sf.persist.mapping;

import java.lang.reflect.Method;

import net.sf.persist.PersistException;

public class DefaultPersistAttributeSetter implements PersistAttributeSetter {
    @Override
    public void setValue(Object instance, Method setter, Object value) {
        try {
            setter.invoke(instance, convertFromPersistentLayer(value));
        } catch (Exception e) {
            String message = String.format("Error setting value [%s] of type [%s] using setter [%s]", value,
                value.getClass(), setter);
            throw new PersistException(message, e);
        }
    }

    protected Object convertFromPersistentLayer(Object value) {
        return value;
    }
}