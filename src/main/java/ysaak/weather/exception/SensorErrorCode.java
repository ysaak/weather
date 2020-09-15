package ysaak.weather.exception;

import ysaak.common.exception.ErrorCode;

public enum SensorErrorCode implements ErrorCode {
    NOT_FOUND_CODE("SENS-NFO-01", "Sensor not found with code '%s'"),
    DISABLED_SENSOR("SENS-INA-01", "Sensor is disabled"),

    INVALID_CODE_LENGTH("SENS-VAL-01", "Code length must be between %s and %s characters"),
    INVALID_NAME_LENGTH("SENS-VAL-02", "Name length must be between %s and %s characters"),
    DEVICE_TYPE_REQUIRED("SENS-VAL-03", "The device type is required"),

    DUPLICATED_CODE("SENS-VAL-04", "Code is already set for another sensor"),

    BATTERY_THRESHOLD_REQUIRED("SENS-VAL-05", "One battery threshold is required"),
    BATTERY_VOLTAGE_THRESHOLD_NEGATIVE("SENS-VAL-06", "Battery voltage threshold is negative"),
    BATTERY_LEVEL_THRESHOLD_VALUE("SENS-VAL-07", "Battery level threshold must be between ]%d,%d["),

    // --
    DATA_NULL_SENSOR_ID("SENS-DST-01", "Sensor ID must be set"),
    DATA_NULL_COLLECTION_TIME("SENS-DST-03", "Collection time must be set"),
    DATA_FUTURE_COLLECTION_TIME("SENS-DST-04", "Collection time cannot be in th future"),
    DATA_NO_DATA_SET("SENS-DST-05", "At least one data must be set"),
    DATA_INVALID_TEMPERATURE_VALUE("SENS-DST-06", "Temperature must be between [%d, %d]"),
    DATA_INVALID_HUMIDITY_VALUE("SENS-DST-06", "Humidity must be between [%d, %d]"),
    ;

    private final String code;
    private final String message;

    SensorErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
