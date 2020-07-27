package net.sf.persist.mapping;

import java.lang.reflect.Method;

public class DefaultPersistAttributeWriter implements PersistAttributeWriter {
    @Override
    public Class getJDBCDataType(Method setter) {
        return setter.getParameterTypes()[0];
    }

    @Override
    public void setValue(Object instance, Method setter, Object value) throws Exception {
        setter.invoke(instance, value);
    }
}
