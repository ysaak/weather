package ysaak.weather.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SENSOR")
public class Sensor {
    @Id
    @GeneratedValue(generator = "SUUID")
    @GenericGenerator(name = "SUUID", strategy = "ysaak.common.dao.ShortUuidGenerator")
    @Column(name = "SENS_ID", nullable = false, updatable = false)
    private String id;

    @Column(name = "SENS_CODE", nullable = false, updatable = false)
    private String code;

    @Column(name = "SENS_NAME", nullable = false)
    private String name;

    @Column(name = "SENS_DESCRIPTION")
    private String description;

    @Column(name = "SENS_DEVICE_TYPE", nullable = false)
    private DeviceType deviceType;

    @Column(name = "SENS_DEVICE_ADDRESS")
    private String deviceAddress;

    @Column(name = "SENS_ON_BATTERY")
    private boolean onBattery;

    @Column(name = "SENS_BATTERY_VOLTAGE_THRESHOLD")
    private Double batteryVoltageThreshold;

    @Column(name = "SENS_BATTERY_LEVEL_THRESHOLD")
    private Integer batteryLevelThreshold;

    @Column(name = "SENS_ENABLED", nullable = false)
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "SENS_LAST_DATA_ID")
    private SensorData lastData;

    @Column(name = "SENS_LAST_BATTERY_VALUE")
    private Double lastBatteryValue;

    @Column(name = "SENS_BATTERY_WARNING")
    private boolean batteryWarning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public boolean isOnBattery() {
        return onBattery;
    }

    public void setOnBattery(boolean onBattery) {
        this.onBattery = onBattery;
    }

    public Double getBatteryVoltageThreshold() {
        return batteryVoltageThreshold;
    }

    public void setBatteryVoltageThreshold(Double batteryVoltageThreshold) {
        this.batteryVoltageThreshold = batteryVoltageThreshold;
    }

    public Integer getBatteryLevelThreshold() {
        return batteryLevelThreshold;
    }

    public void setBatteryLevelThreshold(Integer batteryLevelThreshold) {
        this.batteryLevelThreshold = batteryLevelThreshold;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SensorData getLastData() {
        return lastData;
    }

    public void setLastData(SensorData lastData) {
        this.lastData = lastData;
    }

    public Double getLastBatteryValue() {
        return lastBatteryValue;
    }

    public void setLastBatteryValue(Double lastBatteryValue) {
        this.lastBatteryValue = lastBatteryValue;
    }

    public boolean isBatteryWarning() {
        return batteryWarning;
    }

    public void setBatteryWarning(boolean batteryWarning) {
        this.batteryWarning = batteryWarning;
    }
}
