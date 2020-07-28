package net.sf.persist;

import net.sf.persist.mapping.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PersistRegistry {
    private static final Logger LOG = LoggerFactory.getLogger("persist.engine");
    private static final PersistRegistry INSTANCE = new PersistRegistry();
    private static final DefaultPersistAttributeWriter DEFAULT_PERSIST_ATTRIBUTE_WRITER = new DefaultPersistAttributeWriter();
    private static final DefaultPersistAttributeReader DEFAULT_PERSIST_ATTRIBUTE_READER = new DefaultPersistAttributeReader();

    private final Map<Class, PersistAttributeReader> persistAttributeReaders = new ConcurrentHashMap<>();
    private final Map<Class, PersistAttributeWriter> persistAttributeWriters = new ConcurrentHashMap<>();

    public static PersistRegistry getInstance() {
        return INSTANCE;
    }

    private PersistRegistry() {
        registerPersistAttributeReader(Optional.class, new OptionalPersistAttributeReader());
        registerPersistAttributeWriter(Optional.class, new OptionalPersistAttributeWriter());
    }

    public void registerPersistAttributeReader(Class clazz, PersistAttributeReader persistAttributeReader) {
        if (persistAttributeReaders.containsKey(clazz)) {
            LOG.warn("Overriding PersistAttributeReader for type {}", clazz);
        }
        persistAttributeReaders.put(clazz, persistAttributeReader);
    }

    public void registerPersistAttributeWriter(Class clazz, PersistAttributeWriter persistAttributeWriter) {
        if (persistAttributeReaders.containsKey(clazz)) {
            LOG.warn("Overriding PersistAttributeWriter for type {}", clazz);
        }
        persistAttributeWriters.put(clazz, persistAttributeWriter);
    }

    public PersistAttributeReader getPersistAttributeReader(Method getter) {
        return persistAttributeReaders.getOrDefault(getter.getReturnType(), DEFAULT_PERSIST_ATTRIBUTE_READER);
    }

    public PersistAttributeWriter getPersistAttributeWriter(Method setter) {
        Class<?>[] parameterTypes = setter.getParameterTypes();

        return persistAttributeWriters.getOrDefault(parameterTypes[0], DEFAULT_PERSIST_ATTRIBUTE_WRITER);
    }
}
