package ysaak.weather.api.converter.sensor;

import org.springframework.stereotype.Component;
import ysaak.common.converter.AbstractConverter;
import ysaak.weather.api.dto.sensor.SensorEditDto;
import ysaak.weather.data.DeviceType;
import ysaak.weather.data.Sensor;

@Component
public class SensorEditDtoToSensorConverter extends AbstractConverter<SensorEditDto, Sensor> {
    @Override
    protected Sensor safeConvert(SensorEditDto object) {
        Sensor sensor = new Sensor();
        sensor.setCode(object.getCode());
        sensor.setName(object.getName());
        sensor.setDescription(object.getDescription());
        sensor.setOnBattery(object.isOnBattery());
        sensor.setBatteryVoltageThreshold(object.getBatteryVoltageThreshold());
        sensor.setBatteryLevelThreshold(object.getBatteryLevelThreshold());
        sensor.setDeviceType(toEnum(object.getDeviceType(), DeviceType.class));
        sensor.setDeviceAddress(object.getDeviceAddress());
        return sensor;
    }
}
