package ysaak.weather.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "SENSOR_DATA")
public class SensorData {
    @Id
    @GeneratedValue(generator = "SUUID")
    @GenericGenerator(name = "SUUID", strategy = "ysaak.common.dao.ShortUuidGenerator")
    @Column(name = "SEDA_ID", nullable = false, updatable = false)
    private String id;

    @Column(name = "SEDA_SENS_ID", nullable = false, updatable = false)
    private String sensorId;

    @Column(name = "SEDA_RECEPTION_TIME", nullable = false, updatable = false)
    private LocalDateTime receptionTime;

    @Column(name = "SEDA_REACHABLE", updatable = false)
    private boolean reachable;

    @Column(name = "SEDA_TEMPERATURE", updatable = false)
    private Double temperature;

    @Column(name = "SEDA_HUMIDITY", updatable = false)
    private Double humidity;

    @Column(name = "SEDA_BATTERY_VOLTAGE", updatable = false)
    private Double batteryVoltage;

    @Column(name = "SEDA_BATTERY_LEVEL", updatable = false)
    private Integer batteryLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(LocalDateTime receptionTime) {
        this.receptionTime = receptionTime;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Double batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
