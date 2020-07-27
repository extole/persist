package net.sf.persist.mapping;

import java.lang.reflect.Method;

public interface PersistAttributeWriter {
    Class getJDBCDataType(Method setter);

    void setValue(Object instance, Method setter, Object value) throws Exception;
}
