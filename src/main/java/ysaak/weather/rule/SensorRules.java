package ysaak.weather.rule;

import ysaak.common.exception.FunctionalException;
import ysaak.common.util.Validate;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.exception.SensorErrorCode;

import java.time.LocalDateTime;

public final class SensorRules {
    private SensorRules() { /**/ }

    public static void validate(Sensor sensor) throws FunctionalException {
        Validate.length(sensor.getCode(), 5, 20, SensorErrorCode.INVALID_CODE_LENGTH, 5, 20);
        Validate.length(sensor.getName(), 5, 200, SensorErrorCode.INVALID_NAME_LENGTH, 5, 200);
        Validate.notNull(sensor.getDeviceType(), SensorErrorCode.DEVICE_TYPE_REQUIRED);

        if (sensor.isOnBattery()) {
            Validate.isTrue(atLeastOneThresholdSet(sensor), SensorErrorCode.BATTERY_THRESHOLD_REQUIRED);

            if (sensor.getBatteryVoltageThreshold() != null) {
                Validate.isTrue(sensor.getBatteryVoltageThreshold() >= 0, SensorErrorCode.BATTERY_VOLTAGE_THRESHOLD_NEGATIVE);
            }

            if (sensor.getBatteryLevelThreshold() != null) {
                Validate.between(sensor.getBatteryLevelThreshold(), 0, 100, false, SensorErrorCode.BATTERY_LEVEL_THRESHOLD_VALUE, 0, 100);
            }
        }
    }

    private static boolean atLeastOneThresholdSet(Sensor sensor) {
        return sensor.getBatteryVoltageThreshold() != null
                || sensor.getBatteryLevelThreshold() != null;
    }

    public static void validateData(SensorData data) throws FunctionalException {
        Validate.notNull(data.getSensorId(), SensorErrorCode.DATA_NULL_SENSOR_ID);
        Validate.notNull(data.getReceptionTime(), SensorErrorCode.DATA_NULL_COLLECTION_TIME);
        Validate.isFalse(data.getReceptionTime().isAfter(LocalDateTime.now()), SensorErrorCode.DATA_FUTURE_COLLECTION_TIME);

        if (data.isReachable()) {
            Validate.isTrue(atLeastOneSensedDataSet(data), SensorErrorCode.DATA_NO_DATA_SET);

            if (data.getTemperature() != null) {
                Validate.between(data.getTemperature(), -50., 100., true, SensorErrorCode.DATA_INVALID_TEMPERATURE_VALUE, -50, 100);
            }

            if (data.getHumidity() != null) {
                Validate.between(data.getTemperature(), 0., 100., true, SensorErrorCode.DATA_INVALID_HUMIDITY_VALUE, 0, 100);
            }
        }
    }

    private static boolean atLeastOneSensedDataSet(SensorData data) {
        return data.getTemperature() != null && data.getHumidity() != null;
    }

    public static boolean isBatteryLow(Sensor sensor, SensorData data) {
        boolean batteryWarning = false;

        if (sensor.getBatteryVoltageThreshold() != null) {
            batteryWarning = data.getBatteryVoltage() <= sensor.getBatteryVoltageThreshold();
        }

        if (!batteryWarning && sensor.getBatteryLevelThreshold() != null) {
            batteryWarning = data.getBatteryLevel() <= sensor.getBatteryLevelThreshold();
        }

        return batteryWarning;
    }
}
