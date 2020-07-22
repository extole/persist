package net.sf.persist;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.persist.mapping.DefaultPersistAttributeGetter;
import net.sf.persist.mapping.DefaultPersistAttributeSetter;
import net.sf.persist.mapping.OptionalPersistAttributeGetter;
import net.sf.persist.mapping.OptionalPersistAttributeSetter;
import net.sf.persist.mapping.PersistAttributeGetter;
import net.sf.persist.mapping.PersistAttributeSetter;

public class PersistRegistry {
    private static final Logger LOG = LoggerFactory.getLogger("persist.engine");
    private static final PersistRegistry INSTANCE = new PersistRegistry();

    private Map<Class, PersistAttributeGetter> persistAttributeGetters = new ConcurrentHashMap<>();
    private Map<Class, PersistAttributeSetter> persistAttributeSetters = new ConcurrentHashMap<>();

    public static PersistRegistry getInstance() {
        return INSTANCE;
    }

    private PersistRegistry() {
        registerPersistAttributeGetter(Optional.class, new OptionalPersistAttributeGetter());
        registerPersistAttributeSetter(Optional.class, new OptionalPersistAttributeSetter());
    }

    public void registerPersistAttributeGetter(Class clazz, PersistAttributeGetter persistAttributeGetter) {
        if (persistAttributeGetters.containsKey(clazz)) {
            LOG.warn(String.format("Overriding PersistAttributeGetter for type %s", clazz));
        }
        persistAttributeGetters.put(clazz, persistAttributeGetter);
    }

    public void registerPersistAttributeSetter(Class clazz, PersistAttributeSetter persistAttributeSetter) {
        if (persistAttributeGetters.containsKey(clazz)) {
            LOG.warn(String.format("Overriding PersistAttributeSetter for type %s", clazz));
        }
        persistAttributeSetters.put(clazz, persistAttributeSetter);
    }

    public PersistAttributeGetter findPersistAttributeGetter(Method getter) {
        return persistAttributeGetters.getOrDefault(getter.getReturnType(), new DefaultPersistAttributeGetter());
    }

    public PersistAttributeSetter findPersistAttributeSetter(Method setter) {
        Class<?>[] parameterTypes = setter.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new PersistException(String.format("%s is not a valid setter", setter));
        }
        return persistAttributeSetters.getOrDefault(parameterTypes[0], new DefaultPersistAttributeSetter());
    }
}
