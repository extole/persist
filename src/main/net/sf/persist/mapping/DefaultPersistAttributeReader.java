package net.sf.persist.mapping;

import java.lang.reflect.Method;

public class DefaultPersistAttributeReader implements PersistAttributeReader {
    @Override
    public Object getValue(Object instance, Method getter) throws Exception {
        return getter.invoke(instance);
    }
}