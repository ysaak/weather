package ysaak.weather.api.converter.sensor;


import org.springframework.stereotype.Component;
import ysaak.common.converter.AbstractConverter;
import ysaak.weather.api.dto.sensor.SensorDto;
import ysaak.weather.data.Sensor;

@Component
public class SensorToSensorDtoConverter extends AbstractConverter<Sensor, SensorDto> {
    @Override
    protected SensorDto safeConvert(Sensor object) {
        return new SensorDto(
                object.getCode(),
                object.getName(),
                object.getDescription(),
                fromEnum(object.getDeviceType()),
                object.getDeviceAddress(),
                object.isEnabled()
        );
    }
}
