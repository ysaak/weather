package ysaak.weather.dao.converter;

import ysaak.common.dao.AbstractEnumConverter;
import ysaak.weather.data.DeviceType;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class DeviceTypeConverter extends AbstractEnumConverter<DeviceType> {
    public DeviceTypeConverter() {
        super(DeviceType.class);
    }
}
