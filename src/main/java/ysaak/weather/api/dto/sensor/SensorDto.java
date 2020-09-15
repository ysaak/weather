package ysaak.weather.api.dto.sensor;

public class SensorDto {
    private final String code;
    private final String name;
    private final String description;
    private final String deviceType;
    private final String deviceAddress;
    private final boolean enabled;

    public SensorDto(String code, String name, String description, String deviceType, String deviceAddress, boolean enabled) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.deviceType = deviceType;
        this.deviceAddress = deviceAddress;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }
}
