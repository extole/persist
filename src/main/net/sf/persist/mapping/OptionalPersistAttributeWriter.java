package net.sf.persist.mapping;

import net.sf.persist.PersistException;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalPersistAttributeWriter implements PersistAttributeWriter {
    @Override
    public void setValue(Object instance, Method setter, Object value) throws Exception {
        setter.invoke(instance, Optional.ofNullable(value));
    }

    @Override
    public Class getJDBCDataType(Method setter) {
        Type setterParameterType = setter.getGenericParameterTypes()[0];
        checkTypeIsParameterized(setterParameterType, setter);
        return getParameterizedType(setterParameterType);
    }

    private void checkTypeIsParameterized(Type genericParameterType, Method setter) {
        if (!(genericParameterType instanceof ParameterizedType)) {
            throw new PersistException(String.format("Optional of setter [%s] must be parameterized", setter));
        }
    }

    private Class getParameterizedType(Type genericParameterType) {
        return (Class) ((ParameterizedType) genericParameterType).getActualTypeArguments()[0];
    }
}