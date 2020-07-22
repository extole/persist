package net.sf.persist.mapping;

import java.util.Optional;

public class OptionalPersistAttributeGetter extends DefaultPersistAttributeGetter {
    @Override
    protected Object convertForPersistentLayer(Object value) {
        return ((Optional) value).orElse(null);
    }
}