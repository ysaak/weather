package ysaak.common.dao;

import com.google.common.collect.ImmutableBiMap;

import javax.persistence.AttributeConverter;

public class AbstractEnumConverter<E extends Enum<?> & SerializableEnum> implements AttributeConverter<E, String> {
    private final ImmutableBiMap<String, E> convertMap;

    public AbstractEnumConverter(Class<E> serializableEnumClass) {
        final ImmutableBiMap.Builder<String, E> builder = ImmutableBiMap.builder();

        for (E enumConstant : serializableEnumClass.getEnumConstants()) {
            builder.put(enumConstant.getDbCode(), enumConstant);
        }

        this.convertMap = builder.build();
    }

    @Override
    public String convertToDatabaseColumn(E e) {
        return this.convertMap.inverse().get(e);
    }

    @Override
    public E convertToEntityAttribute(String s) {
        return this.convertMap.get(s);
    }
}
