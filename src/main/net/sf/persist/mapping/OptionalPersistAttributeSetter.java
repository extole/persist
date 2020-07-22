package net.sf.persist.mapping;

import java.util.Optional;

public class OptionalPersistAttributeSetter extends DefaultPersistAttributeSetter {
    @Override
    protected Object convertFromPersistentLayer(Object value) {
        return Optional.ofNullable(value);
    }
}