package ysaak.weather.dto.view;

import java.util.List;

public class SensorGraphViewDto {
    private final long minDate;
    private final long maxDate;
    private final List<GraphDataDto> temperatureData;
    private final List<GraphDataDto> humidityData;

    public SensorGraphViewDto(long minDate, long maxDate, List<GraphDataDto> temperatureData, List<GraphDataDto> humidityData) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.temperatureData = temperatureData;
        this.humidityData = humidityData;
    }

    public long getMinDate() {
        return minDate;
    }

    public long getMaxDate() {
        return maxDate;
    }

    public List<GraphDataDto> getTemperatureData() {
        return temperatureData;
    }

    public List<GraphDataDto> getHumidityData() {
        return humidityData;
    }

    public static class GraphDataDto {
        private final long time;
        private final Double value;

        public GraphDataDto(long time, Double value) {
            this.time = time;
            this.value = value;
        }

        public long getTime() {
            return time;
        }

        public Double getValue() {
            return value;
        }
    }
}
