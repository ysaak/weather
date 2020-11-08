package ysaak.weather.view.converter;


import org.springframework.stereotype.Component;
import ysaak.common.converter.AbstractConverter;
import ysaak.common.util.DateUtils;
import ysaak.common.util.MathUtils;
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

        if (object.getLastData() != null) {
            SensorData sensorData = object.getLastData();
            temperature = MathUtils.nullSafeRound(sensorData.getTemperature(), 1);
            humidity = MathUtils.nullSafeRound(sensorData.getHumidity(), 0);
            lastUpdateTime = LAST_UPDATE_TIME_FORMATTER.format(
                    DateUtils.toLocalDateTime(object.getLastData().getReceptionTime())
            );
        }

        return new SensorSummaryDto(
                object.getCode(),
                object.getName(),
                temperature,
                humidity,
                lastUpdateTime,
                object.isBatteryWarning(),
                object.isUnreachableWarning()
        );
    }
}
