package net.sf.persist.mapping;

import java.lang.reflect.Method;
import java.util.Optional;

public class OptionalPersistAttributeReader implements PersistAttributeReader {
    @Override
    public Object getValue(Object instance, Method getter) throws Exception {
        Optional value = (Optional) getter.invoke(instance);
        return value.orElse(null);
    }
}