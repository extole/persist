package net.sf.persist.mapping;

import java.lang.reflect.Method;

public interface PersistAttributeReader {
    Object getValue(Object instance, Method getter) throws Exception;
}