package net.sf.persist.mapping;

import java.lang.reflect.Method;

public interface PersistAttributeSetter {
    void setValue(Object instance, Method setter, Object value);
}