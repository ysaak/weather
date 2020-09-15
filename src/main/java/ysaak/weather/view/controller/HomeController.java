package ysaak.weather.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ysaak.common.exception.FunctionalException;
import ysaak.common.util.DateUtils;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.service.SensorService;
import ysaak.weather.view.converter.SensorToSensorSummaryDtoConverter;
import ysaak.weather.view.dto.GraphDataDto;
import ysaak.weather.view.dto.SensorSummaryDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final SensorService sensorService;

    private final SensorToSensorSummaryDtoConverter sensorToSensorSummaryDtoConverter;

    @Autowired
    public HomeController(SensorService sensorService, SensorToSensorSummaryDtoConverter sensorToSensorSummaryDtoConverter) {
        this.sensorService = sensorService;
        this.sensorToSensorSummaryDtoConverter = sensorToSensorSummaryDtoConverter;
    }

    @GetMapping(path = "/")
    public String indexAction(ModelMap modelMap) {
        final List<Sensor> sensors = this.sensorService.findAll();
        final List<SensorSummaryDto> sensorSummaryDtos = sensorToSensorSummaryDtoConverter.convert(sensors);
        sensorSummaryDtos.sort(Comparator.comparing(SensorSummaryDto::getName));

        modelMap.put("sensors", sensorSummaryDtos);

        return "index";
    }

    @GetMapping("/sensor/{code}")
    public String detailsAction(ModelMap modelMap, @PathVariable("code") String code) throws FunctionalException {
        Sensor sensor = sensorService.findByCode(code);

        LocalDateTime minDate = DateUtils.getDateTimeUtc().minusDays(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime maxDate = DateUtils.getDateTimeUtc().plusHours(1).truncatedTo(ChronoUnit.HOURS);

        List<SensorData> sensorData = sensorService.findDataBetween(sensor.getId(), minDate, maxDate);
        sensorData.sort(Comparator.comparing(SensorData::getReceptionTime));

        List<GraphDataDto> temperatureData = new ArrayList<>(sensorData.size());
        List<GraphDataDto> humidityData = new ArrayList<>(sensorData.size());

        for (SensorData data : sensorData) {
            if (data.isReachable()) {
                temperatureData.add(new GraphDataDto(
                        DateUtils.toLocalTimestamp(data.getReceptionTime()),
                        data.getTemperature()
                ));

                humidityData.add(new GraphDataDto(
                        DateUtils.toLocalTimestamp(data.getReceptionTime()),
                        data.getHumidity()
                ));
            }
        }

        modelMap.put("sensorName", sensor.getName());

        modelMap.put("temperatureData", temperatureData);
        modelMap.put("humidityData", humidityData);

        modelMap.put("minDate", DateUtils.toLocalTimestamp(minDate));
        modelMap.put("maxDate", DateUtils.toLocalTimestamp(maxDate));

        return "details";
    }
}
