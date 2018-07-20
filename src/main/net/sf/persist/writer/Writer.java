package net.sf.persist.writer;

public interface Writer<T, U> {

    T writeToStrongType(U serializedValue);

    U readFromStrongType(T stronglyTypedValue);
}
