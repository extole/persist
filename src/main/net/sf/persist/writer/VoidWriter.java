package net.sf.persist.writer;

public class VoidWriter implements Writer {

    @Override
    public Object writeToStrongType(Object serializedValue) {
        return null;
    }

    @Override
    public Object readFromStrongType(Object stronglyTypedValue) {
        return null;
    }

}
