package ysaak.weather;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ysaak.common.util.DateUtils;
import ysaak.weather.dao.repository.SensorDataRepository;
import ysaak.weather.dao.repository.SensorRepository;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GenerateTestData {
    private final SensorRepository sensorRepository;
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public GenerateTestData(SensorRepository sensorRepository, SensorDataRepository sensorDataRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorDataRepository = sensorDataRepository;
    }

    @Test
    @Disabled
    public void generateFakeData() {
        Sensor sensor = sensorRepository.findByCode("SENSOR-001").orElseThrow(RuntimeException::new);

        double temperature = 10.0;
        double temperature_delta = 3.;

        double humidity = 45.;
        double humidity_delta = 3.;

        LocalDateTime fakeDate = DateUtils.getDateTimeUtc();

        List<SensorData> fakeData = new ArrayList<>(100);

        for (int i = 0; i<=100; i++) {
            SensorData data = new SensorData();
            data.setSensorId(sensor.getId());
            data.setReceptionTime(fakeDate);
            data.setReachable(true);
            data.setTemperature(temperature);
            data.setHumidity(humidity);

            fakeData.add(data);

            fakeDate = fakeDate.minusHours(1);
            temperature = randomValue(temperature - temperature_delta, temperature + temperature_delta);
            humidity = randomValue(humidity - humidity_delta, humidity + humidity_delta);
        }

        sensorDataRepository.saveAll(fakeData);
    }
    private double randomValue(double lower, double upper) {
        return (Math.random() * (upper - lower)) + lower;
    }
}
