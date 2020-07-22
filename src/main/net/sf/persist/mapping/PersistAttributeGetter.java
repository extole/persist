package net.sf.persist.mapping;

import java.lang.reflect.Method;

public interface PersistAttributeGetter {
    Object getValue(Object instance, Method getter);
}