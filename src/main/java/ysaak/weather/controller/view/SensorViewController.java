package ysaak.weather.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ysaak.common.exception.FunctionalException;
import ysaak.common.util.DateUtils;
import ysaak.common.util.MathUtils;
import ysaak.weather.converter.view.SensorToSensorViewDtoConverter;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.dto.view.SensorGraphViewDto;
import ysaak.weather.dto.view.SensorViewDto;
import ysaak.weather.service.SensorService;
import ysaak.weather.view.converter.SensorToSensorSummaryDtoConverter;
import ysaak.weather.view.dto.SensorSummaryDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("view/sensors")
@CrossOrigin
public class SensorViewController {
    private final SensorService sensorService;

    private final SensorToSensorSummaryDtoConverter sensorToSensorSummaryDtoConverter;
    private final SensorToSensorViewDtoConverter sensorToSensorViewDtoConverter;

    @Autowired
    public SensorViewController(SensorService sensorService, SensorToSensorSummaryDtoConverter sensorToSensorSummaryDtoConverter, SensorToSensorViewDtoConverter sensorToSensorViewDtoConverter) {
        this.sensorService = sensorService;
        this.sensorToSensorSummaryDtoConverter = sensorToSensorSummaryDtoConverter;
        this.sensorToSensorViewDtoConverter = sensorToSensorViewDtoConverter;
    }

    @GetMapping("/")
    public List<SensorSummaryDto> indexAction() {
        final List<Sensor> sensors = this.sensorService.findAll();
        final List<SensorSummaryDto> sensorSummaryDtos = sensorToSensorSummaryDtoConverter.convert(sensors);
        sensorSummaryDtos.sort(Comparator.comparing(SensorSummaryDto::getName));
        return sensorSummaryDtos;
    }

    @GetMapping("/{code}")
    public SensorViewDto viewAction(@PathVariable("code") final String code) throws FunctionalException {
        final Sensor sensor = this.sensorService.findByCode(code);
        return sensorToSensorViewDtoConverter.convert(sensor);
    }

    @GetMapping("/{code}/graph/day")
    public SensorGraphViewDto graphDayAction(@PathVariable("code") final String code) throws FunctionalException {
        final Sensor sensor = this.sensorService.findByCode(code);

        LocalDateTime minDate = DateUtils.getDateTimeUtc().minusDays(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime maxDate = DateUtils.getDateTimeUtc().plusHours(1).truncatedTo(ChronoUnit.HOURS);

        List<SensorData> sensorData = sensorService.findDataBetween(sensor.getId(), minDate, maxDate);
        sensorData.sort(Comparator.comparing(SensorData::getReceptionTime));

        List<SensorGraphViewDto.GraphDataDto> temperatureData = new ArrayList<>(sensorData.size());
        List<SensorGraphViewDto.GraphDataDto> humidityData = new ArrayList<>(sensorData.size());

        for (SensorData data : sensorData) {
            long time = DateUtils.toLocalTimestamp(data.getReceptionTime());
            Double temperature = null;
            Double humidity = null;

            if (data.isReachable()) {
                temperature = MathUtils.nullSafeRound(data.getTemperature(), 1);
                humidity = MathUtils.nullSafeRound(data.getHumidity(), 0);
            }

            temperatureData.add(new SensorGraphViewDto.GraphDataDto(time, temperature));
            humidityData.add(new SensorGraphViewDto.GraphDataDto(time, humidity));
        }

        return new SensorGraphViewDto(
                DateUtils.toLocalTimestamp(minDate),
                DateUtils.toLocalTimestamp(maxDate),
                temperatureData,
                humidityData
        );
    }
}
