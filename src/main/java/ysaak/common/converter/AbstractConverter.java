package ysaak.common.converter;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractConverter<FROM, TO> {
    protected abstract TO safeConvert(FROM object);

    public final TO convert(FROM object) {
        if (object == null) {
            return null;
        }

        return safeConvert(object);
    }

    public final List<TO> convert(List<FROM> objects) {
        if (objects == null) {
            return new ArrayList<>();
        }

        return objects.stream()
                .filter(Objects::nonNull)
                .map(this::safeConvert)
                .collect(Collectors.toList());
    }

    protected final String fromEnum(Enum<?> enumValue) {
        return enumValue != null ? enumValue.toString() : null;
    }

    protected final <E extends Enum<E>> E toEnum(String value, Class<E> enumClass) {
        if (value != null) {
            try {
                return Enum.valueOf(enumClass, value);
            }
            catch (IllegalArgumentException e) {
                LoggerFactory.getLogger(getClass()).warn("Unkown value '{}' for enum '{}'", value, enumClass.getName());
            }
        }
        return null;
    }
}
