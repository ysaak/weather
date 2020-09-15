package ysaak.weather.view.converter;


import org.springframework.stereotype.Component;
import ysaak.common.converter.AbstractConverter;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.view.dto.SensorSummaryDto;

import java.time.format.DateTimeFormatter;

@Component
public class SensorToSensorSummaryDtoConverter extends AbstractConverter<Sensor, SensorSummaryDto> {
    private static final DateTimeFormatter LAST_UPDATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    protected SensorSummaryDto safeConvert(Sensor object) {
        Double temperature = null;
        Double humidity = null;
        String lastUpdateTime = null;
        boolean unreachable = false;

        if (object.getLastData() != null) {
            SensorData sensorData = object.getLastData();
            temperature = sensorData.getTemperature();
            humidity = sensorData.getHumidity();
            lastUpdateTime = LAST_UPDATE_TIME_FORMATTER.format(object.getLastData().getReceptionTime());
            unreachable = !sensorData.isReachable();
        }

        return new SensorSummaryDto(
                object.getCode(),
                object.getName(),
                temperature,
                humidity,
                lastUpdateTime,
                object.isBatteryWarning(),
                unreachable
        );
    }
}
