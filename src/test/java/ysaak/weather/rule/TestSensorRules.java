package ysaak.weather.rule;

import org.junit.jupiter.api.Test;
import ysaak.common.exception.FunctionalException;
import ysaak.test.SensorTestData;
import ysaak.test.TestUtils;
import ysaak.weather.data.Sensor;
import ysaak.weather.exception.SensorErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class TestSensorRules {

    @Test
    public void testValidate_valid() throws FunctionalException {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();

        // When
        SensorRules.validate(sensor);

        // Then
        // Success
    }

    @Test
    public void testValidate_nameTooShort() {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();
        sensor.setName(TestUtils.generateRandomString(4));

        // When
        Throwable thrown = catchThrowable(() -> SensorRules.validate(sensor));

        // Then
        assertThat(thrown)
                .withFailMessage("No exception thrown")
                .isNotNull()
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", SensorErrorCode.INVALID_NAME_LENGTH);
    }

    @Test
    public void testValidate_nameTooLong() {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();
        sensor.setName(TestUtils.generateRandomString(201));

        // When
        Throwable thrown = catchThrowable(() -> SensorRules.validate(sensor));

        // Then
        assertThat(thrown)
                .withFailMessage("No exception thrown")
                .isNotNull()
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", SensorErrorCode.INVALID_NAME_LENGTH);
    }

    @Test
    public void testValidate_codeTooShort() {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();
        sensor.setCode(TestUtils.generateRandomString(4));

        // When
        Throwable thrown = catchThrowable(() -> SensorRules.validate(sensor));

        // Then
        assertThat(thrown)
                .withFailMessage("No exception thrown")
                .isNotNull()
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", SensorErrorCode.INVALID_CODE_LENGTH);
    }

    @Test
    public void testValidate_codeTooLong() {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();
        sensor.setCode(TestUtils.generateRandomString(21));

        // When
        Throwable thrown = catchThrowable(() -> SensorRules.validate(sensor));

        // Then
        assertThat(thrown)
                .withFailMessage("No exception thrown")
                .isNotNull()
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", SensorErrorCode.INVALID_CODE_LENGTH);
    }

    @Test
    public void testValidate_nullCapability() {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();
        sensor.setDeviceType(null);

        // When
        Throwable thrown = catchThrowable(() -> SensorRules.validate(sensor));

        // Then
        assertThat(thrown)
                .withFailMessage("No exception thrown")
                .isNotNull()
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", SensorErrorCode.DEVICE_TYPE_REQUIRED);
    }
}
