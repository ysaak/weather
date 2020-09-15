package ysaak.weather.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ysaak.common.exception.FunctionalException;
import ysaak.weather.api.converter.sensor.SensorEditDtoToSensorConverter;
import ysaak.weather.api.converter.sensor.SensorToSensorDtoConverter;
import ysaak.weather.api.dto.sensor.SensorDto;
import ysaak.weather.api.dto.sensor.SensorEditDto;
import ysaak.weather.data.Sensor;
import ysaak.weather.service.DataFetcherService;
import ysaak.weather.service.SensorService;

import java.util.List;

@RestController
@RequestMapping("api/sensor")
public class SensorApiController {

    private final SensorService sensorService;
    private final DataFetcherService dataFetcherService;

    private final SensorToSensorDtoConverter sensorToSensorDtoConverter;
    private final SensorEditDtoToSensorConverter sensorEditDtoToSensorConverter;

    @Autowired
    public SensorApiController(SensorService sensorService, DataFetcherService dataFetcherService, SensorToSensorDtoConverter sensorToSensorDtoConverter, SensorEditDtoToSensorConverter sensorEditDtoToSensorConverter) {
        this.sensorService = sensorService;
        this.dataFetcherService = dataFetcherService;
        this.sensorToSensorDtoConverter = sensorToSensorDtoConverter;
        this.sensorEditDtoToSensorConverter = sensorEditDtoToSensorConverter;
    }

    @GetMapping(path = "/")
    public List<SensorDto> indexAction() {
        List<Sensor> sensors = sensorService.findAll();
        return sensorToSensorDtoConverter.convert(sensors);
    }

    @GetMapping("/{code}")
    public SensorDto getAction(@PathVariable("code") final String code) throws FunctionalException {
        Sensor sensor = sensorService.findByCode(code);
        return sensorToSensorDtoConverter.convert(sensor);
    }

    @PostMapping(path = "/create")
    public SensorDto createAction(@RequestBody final SensorEditDto sensorEditDto) throws FunctionalException {
        final Sensor sensorToCreate = sensorEditDtoToSensorConverter.convert(sensorEditDto);

        final Sensor savedSensor = sensorService.create(sensorToCreate);

        return sensorToSensorDtoConverter.convert(savedSensor);
    }

    @PostMapping(path = "/{code}")
    public SensorDto updateAction(@RequestBody final SensorEditDto sensorEditDto) throws FunctionalException {
        final Sensor sensorToUpdate = sensorEditDtoToSensorConverter.convert(sensorEditDto);

        final Sensor savedSensor = sensorService.update(sensorToUpdate);

        return sensorToSensorDtoConverter.convert(savedSensor);
    }

    @PostMapping(path = "/{code}/fetch-data")
    public void fetchDataAction(@PathVariable("code") final String code) throws FunctionalException {
        dataFetcherService.fetchDataForSensor(code);
    }

    @PostMapping("/{code}/enable")
    public void enableSensorAction(@PathVariable("code") final String code) throws FunctionalException {
        sensorService.setSensorEnabled(code, true);
    }

    @PostMapping("/{code}/disable")
    public void disableSensorAction(@PathVariable("code") final String code) throws FunctionalException {
        sensorService.setSensorEnabled(code, false);
    }
}
