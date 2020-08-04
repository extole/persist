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
import java.util.Objects;
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
        PersistAttributeReader overriddenReader = persistAttributeReaders.put(clazz, persistAttributeReader);
        if (Objects.nonNull(overriddenReader)) {
            LOG.warn("Overriding {} PersistAttributeReader for type {} with {}",
                    new Object[]{overriddenReader.getClass(), clazz, persistAttributeReader.getClass()});
        }
    }

    public void registerPersistAttributeWriter(Class clazz, PersistAttributeWriter persistAttributeWriter) {
        PersistAttributeWriter overriddenWriter = persistAttributeWriters.put(clazz, persistAttributeWriter);
        if (Objects.nonNull(overriddenWriter)) {
            LOG.warn("Overriding {} PersistAttributeWriter for type {} with {}",
                    new Object[]{overriddenWriter.getClass(), clazz, persistAttributeWriter.getClass()});
        }
    }

    public PersistAttributeReader getPersistAttributeReader(Method getter) {
        return persistAttributeReaders.getOrDefault(getter.getReturnType(), DEFAULT_PERSIST_ATTRIBUTE_READER);
    }

    public PersistAttributeWriter getPersistAttributeWriter(Method setter) {
        Class<?>[] parameterTypes = setter.getParameterTypes();

        return persistAttributeWriters.getOrDefault(parameterTypes[0], DEFAULT_PERSIST_ATTRIBUTE_WRITER);
    }
}
