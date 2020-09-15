package ysaak.weather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ysaak.common.exception.FunctionalException;
import ysaak.test.SensorTestData;
import ysaak.weather.dao.repository.SensorRepository;
import ysaak.weather.data.Sensor;
import ysaak.weather.exception.SensorErrorCode;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
public class TestSensorService {
    @MockBean
    private SensorRepository sensorRepository;

    @Autowired
    private SensorService sensorService;

    @BeforeEach
    public void setMockOutput() {
        Sensor existingSensor = SensorTestData.getValidSensor();

        Mockito.when(sensorRepository.findByCode(existingSensor.getCode())).thenReturn(Optional.of(existingSensor));
    }

    @Test
    public void testCreate_success() throws FunctionalException {
        // Given
        final Sensor sensor = SensorTestData.getNewSensor();

        final Sensor expectedSavedSensor = SensorTestData.getNewSensor();
        expectedSavedSensor.setId("TEST_SAVE");

        // When
        Mockito.when(sensorRepository.save(Mockito.any(Sensor.class))).thenAnswer(i -> expectedSavedSensor);
        Sensor createdSensor = sensorService.create(sensor);

        // Then
        assertThat(createdSensor)
                .isNotNull()
                .isEqualToComparingFieldByField(expectedSavedSensor);
    }

    @Test
    public void testCreate_duplicatedCode() {
        // Given
        Sensor sensor = SensorTestData.getValidSensor();

        // When
        Throwable thrown = catchThrowable(() -> sensorService.create(sensor));

        // Then
        assertThat(thrown)
                .withFailMessage("No exception thrown")
                .isNotNull()
                .withFailMessage("Invalid exception type")
                .isInstanceOf(FunctionalException.class)
                .withFailMessage("Invalid error code")
                .hasFieldOrPropertyWithValue("error", SensorErrorCode.DUPLICATED_CODE);
    }

    @Test
    public void testUpdate_success() throws FunctionalException {
        // Given
        final Sensor sensor = SensorTestData.getValidSensor();
        sensor.setName(sensor.getName() + "_N");
        sensor.setDescription(sensor.getDescription() + "_N");

        // When
        Mockito.when(sensorRepository.save(Mockito.any(Sensor.class))).thenAnswer(i -> sensor);
        Sensor updatedSensor = sensorService.update(sensor);

        // Then
        assertThat(updatedSensor)
                .isNotNull()
                .isEqualToComparingFieldByField(sensor);
    }
}
