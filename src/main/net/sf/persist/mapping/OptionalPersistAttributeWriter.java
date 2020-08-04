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
        checkTypeIsParameterizedWithClass(setterParameterType, setter);
        return getParameterizedType(setterParameterType);
    }

    private void checkTypeIsParameterizedWithClass(Type genericParameterType, Method setter) {
        String errorMessage = String.format("Optional of setter [%s] must be parameterized with an exact class", setter);

        if (!(genericParameterType instanceof ParameterizedType)) {
            throw new PersistException(errorMessage);
        }

        Type typeArgument = ((ParameterizedType) genericParameterType).getActualTypeArguments()[0];
        if (!(typeArgument instanceof Class)) {
            throw new PersistException(errorMessage);
        }
    }

    private Class getParameterizedType(Type genericParameterType) {
        Type typeArgument = ((ParameterizedType) genericParameterType).getActualTypeArguments()[0];

        return (Class) typeArgument;
    }
}
