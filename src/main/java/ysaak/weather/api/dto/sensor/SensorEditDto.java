package ysaak.weather.api.dto.sensor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorEditDto {
    private final String code;
    private final String name;
    private final String description;
    private final String deviceType;
    private final String deviceAddress;
    private final boolean onBattery;
    private final Double batteryVoltageThreshold;
    private final Integer batteryLevelThreshold;

    @JsonCreator
    public SensorEditDto(@JsonProperty("code") String code,
                         @JsonProperty("name") String name,
                         @JsonProperty("description") String description,
                         @JsonProperty("deviceType") String deviceType,
                         @JsonProperty("deviceAddress") String deviceAddress,
                         @JsonProperty("onBattery") boolean onBattery,
                         @JsonProperty("batteryVoltageThreshold") Double batteryVoltageThreshold,
                         @JsonProperty("batteryLevelThreshold") Integer batteryLevelThreshold) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.deviceType = deviceType;
        this.deviceAddress = deviceAddress;
        this.onBattery = onBattery;
        this.batteryVoltageThreshold = batteryVoltageThreshold;
        this.batteryLevelThreshold = batteryLevelThreshold;
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

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public boolean isOnBattery() {
        return onBattery;
    }

    public Double getBatteryVoltageThreshold() {
        return batteryVoltageThreshold;
    }

    public Integer getBatteryLevelThreshold() {
        return batteryLevelThreshold;
    }
}
