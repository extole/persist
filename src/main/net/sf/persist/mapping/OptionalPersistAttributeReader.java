package net.sf.persist.mapping;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class OptionalPersistAttributeReader implements PersistAttributeReader {
    @Override
    public Object getValue(Object instance, Method getter) throws Exception {
        Optional value = (Optional) getter.invoke(instance);
        return Objects.nonNull(value) ? value.orElse(null) : null;
    }
}
