package ysaak.weather.service.datafetcher;

import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;

import java.util.Optional;

public interface DataFetcher {
    Optional<SensorData> fetchData(Sensor sensor);
}
