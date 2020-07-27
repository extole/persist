package net.sf.persist;

import net.sf.persist.mapping.DefaultPersistAttributeReader;
import net.sf.persist.mapping.DefaultPersistAttributeWriter;
import net.sf.persist.mapping.OptionalPersistAttributeReader;
import net.sf.persist.mapping.OptionalPersistAttributeWriter;
import net.sf.persist.mapping.PersistAttributeReader;
import net.sf.persist.mapping.PersistAttributeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PersistRegistry {
    private static final Logger LOG = LoggerFactory.getLogger("persist.engine");
    private static final PersistRegistry INSTANCE = new PersistRegistry();
    public static final DefaultPersistAttributeWriter DEFAULT_PERSIST_ATTRIBUTE_WRITER = new DefaultPersistAttributeWriter();
    public static final DefaultPersistAttributeReader DEFAULT_PERSIST_ATTRIBUTE_READER = new DefaultPersistAttributeReader();

    private Map<Class, PersistAttributeReader> persistAttributeReaders = new ConcurrentHashMap<>();
    private Map<Class, PersistAttributeWriter> persistAttributeWriters = new ConcurrentHashMap<>();

    public static PersistRegistry getInstance() {
        return INSTANCE;
    }

    private PersistRegistry() {
        registerPersistAttributeReader(Optional.class, new OptionalPersistAttributeReader());
        registerPersistAttributeWriter(Optional.class, new OptionalPersistAttributeWriter());
    }

    public void registerPersistAttributeReader(Class clazz, PersistAttributeReader persistAttributeReader) {
        if (persistAttributeReaders.containsKey(clazz)) {
            LOG.warn(String.format("Overriding PersistAttributeReader for type %s", clazz));
        }
        persistAttributeReaders.put(clazz, persistAttributeReader);
    }

    public void registerPersistAttributeWriter(Class clazz, PersistAttributeWriter persistAttributeWriter) {
        if (persistAttributeReaders.containsKey(clazz)) {
            LOG.warn(String.format("Overriding PersistAttributeWriter for type %s", clazz));
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
