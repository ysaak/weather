package ysaak.weather.view.dto;

public class SensorSummaryDto {
    private final String code;
    private final String name;
    private final Double temperature;
    private final Double humidity;
    private final String lastUpdateTime;
    private final boolean lowBattery;
    private final boolean unreachable;

    public SensorSummaryDto(String code, String name, Double temperature, Double humidity, String lastUpdateTime, boolean lowBattery, boolean unreachable) {
        this.code = code;
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.lastUpdateTime = lastUpdateTime;
        this.lowBattery = lowBattery;
        this.unreachable = unreachable;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public boolean isLowBattery() {
        return lowBattery;
    }

    public boolean isUnreachable() {
        return unreachable;
    }
}
