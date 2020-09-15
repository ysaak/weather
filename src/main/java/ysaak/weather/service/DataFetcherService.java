package ysaak.weather.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ysaak.common.exception.FunctionalException;
import ysaak.weather.config.WeatherAppConfig;
import ysaak.weather.dao.specification.SensorSpecification;
import ysaak.weather.data.DeviceType;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.service.datafetcher.DataFetcher;
import ysaak.weather.service.datafetcher.XiaomiMijiaDataFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataFetcherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFetcherService.class);

    private final Map<DeviceType, DataFetcher> dataFetcherMap;

    private final SensorService sensorService;

    @Autowired
    public DataFetcherService(SensorService sensorService, WeatherAppConfig weatherAppConfig) {
        this.sensorService = sensorService;

        this.dataFetcherMap = new HashMap<>(1);
        this.dataFetcherMap.put(DeviceType.XIAOMI_MIJIA, new XiaomiMijiaDataFetcher(weatherAppConfig.getXiaomiMijiaDataFetcher()));
    }

    public void fetchDataForAllSensors() {
        LOGGER.debug("Starting sensor data fetching");

        Specification<Sensor> searchRequest = Specification
                .where(SensorSpecification.isEnabled(true))
                .and(SensorSpecification.isDeviceType(DeviceType.XIAOMI_MIJIA));

        List<Sensor> xiaomiSensors = sensorService.search(searchRequest);
        xiaomiSensors.forEach(this::readAndStoreSensorData);
        LOGGER.debug("End of sensor data fetching");
    }

    public void fetchDataForSensor(String sensorCode) throws FunctionalException {
        final Sensor sensor = sensorService.findByCode(sensorCode);
        readAndStoreSensorData(sensor);
    }

    private void readAndStoreSensorData(final Sensor sensor) {
        LOGGER.debug("Fetching data for sensor {}", sensor.getCode());

        final Optional<SensorData> fetchedData;

        if (dataFetcherMap.containsKey(sensor.getDeviceType())) {
            fetchedData = dataFetcherMap.get(sensor.getDeviceType()).fetchData(sensor);
        }
        else {
            LOGGER.error("No data fetcher for sensor type {}", sensor.getDeviceType());
            fetchedData = Optional.empty();
        }

        fetchedData.ifPresent(data -> {
            try {
                sensorService.pushData(sensor.getCode(), data);
            }
            catch (FunctionalException e) {
                LOGGER.error("Error while storing data for sensor " + sensor.getCode(), e);
            }
        });
    }
}
