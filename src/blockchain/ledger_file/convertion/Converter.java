package blockchain.ledger_file.convertion;

import java.io.UncheckedIOException;
import java.lang.reflect.ParameterizedType;

public abstract class Converter<T> {

    private final short CONVERTER_VERSION_UID;

    public Converter(short CONVERTER_VERSION_UID) {
        this.CONVERTER_VERSION_UID = CONVERTER_VERSION_UID;
    }

    protected abstract T instanceFromBytes(byte[] bytes);

    protected abstract byte[] bytesFromInstance(T instance);

    public short getCONVERTER_VERSION_UID() {
        return CONVERTER_VERSION_UID;
    }

    protected abstract short getOBJECT_TYPE_UID();

    // Tests if the given object o is an instance of T
    public boolean canConvert(Object o) {
        // Initializes an instance of T
        Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return type.isInstance(o);

    }

    public abstract int getByteSize();

}
