package ysaak.test;

import ysaak.weather.data.DeviceType;
import ysaak.weather.data.Sensor;

public final class SensorTestData {
    private SensorTestData() { /**/ }

    public static Sensor getNewSensor() {
        Sensor sensor = new Sensor();
        sensor.setCode("CODE_N");
        sensor.setName("VALID NAME");
        sensor.setDeviceType(DeviceType.XIAOMI_MIJIA);
        return sensor;
    }

    public static Sensor getValidSensor() {
        Sensor sensor = new Sensor();
        sensor.setId("FAKE_01");
        sensor.setCode("CODE_E");
        sensor.setName("VALID NAME");
        sensor.setDescription("Description");
        sensor.setDeviceType(DeviceType.XIAOMI_MIJIA);
        return sensor;
    }
}
