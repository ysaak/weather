package ysaak.weather.converter.view;


import org.springframework.stereotype.Component;
import ysaak.common.converter.AbstractConverter;
import ysaak.common.util.MathUtils;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.dto.view.SensorViewDto;

@Component
public class SensorToSensorViewDtoConverter extends AbstractConverter<Sensor, SensorViewDto> {

    @Override
    protected SensorViewDto safeConvert(Sensor object) {
        return new SensorViewDto(
                object.getCode(),
                object.getName(),
                object.getDescription(),
                object.getLastBatteryValue(),
                object.isBatteryWarning(),
                convertLastData(object.getLastData()));
    }

    private SensorViewDto.LastData convertLastData(SensorData sensorData) {
        return sensorData != null
                ? new SensorViewDto.LastData(
                    sensorData.getReceptionTime(),
                    sensorData.isReachable(),
                    MathUtils.nullSafeRound(sensorData.getTemperature(), 1),
                    MathUtils.nullSafeRound(sensorData.getHumidity(), 0),
                    sensorData.getBatteryVoltage(),
                    sensorData.getBatteryLevel()
                )
                : null;
    }
}
