package ysaak.weather.dto.view;

import java.time.LocalDateTime;

public class SensorViewDto {
    private final String code;
    private final String name;
    private final String description;
    private final Double lastBatteryValue;
    private final boolean batteryWarning;
    private final LastData lastData;

    public SensorViewDto(String code, String name, String description, Double lastBatteryValue, boolean batteryWarning, LastData lastData) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.lastBatteryValue = lastBatteryValue;
        this.batteryWarning = batteryWarning;
        this.lastData = lastData;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getLastBatteryValue() {
        return lastBatteryValue;
    }

    public boolean isBatteryWarning() {
        return batteryWarning;
    }

    public LastData getLastData() {
        return lastData;
    }

    public static class LastData {
        private final LocalDateTime receptionTime;
        private final boolean reachable;
        private final Double temperature;
        private final Double humidity;
        private final Double batteryVoltage;
        private final Integer batteryLevel;

        public LastData(LocalDateTime receptionTime, boolean reachable, Double temperature, Double humidity, Double batteryVoltage, Integer batteryLevel) {
            this.receptionTime = receptionTime;
            this.reachable = reachable;
            this.temperature = temperature;
            this.humidity = humidity;
            this.batteryVoltage = batteryVoltage;
            this.batteryLevel = batteryLevel;
        }

        public LocalDateTime getReceptionTime() {
            return receptionTime;
        }

        public boolean isReachable() {
            return reachable;
        }

        public Double getTemperature() {
            return temperature;
        }

        public Double getHumidity() {
            return humidity;
        }

        public Double getBatteryVoltage() {
            return batteryVoltage;
        }

        public Integer getBatteryLevel() {
            return batteryLevel;
        }
    }
}
