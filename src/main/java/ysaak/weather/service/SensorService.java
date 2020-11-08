package ysaak.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ysaak.common.exception.FunctionalException;
import ysaak.common.util.CollectionUtils;
import ysaak.common.util.DateUtils;
import ysaak.common.util.Validate;
import ysaak.weather.dao.repository.SensorDataRepository;
import ysaak.weather.dao.repository.SensorRepository;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.exception.SensorErrorCode;
import ysaak.weather.rule.SensorRules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, SensorDataRepository sensorDataRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorDataRepository = sensorDataRepository;
    }

    public Sensor findByCode(final String code) throws FunctionalException {
        return sensorRepository.findByCode(code)
                .orElseThrow(() -> SensorErrorCode.NOT_FOUND_CODE.functional(code));
    }

    public List<Sensor> findAll() {
        return CollectionUtils.toList(sensorRepository.findAll());
    }

    public List<Sensor> search(Specification<Sensor> searchRequest) {
        return sensorRepository.findAll(searchRequest);
    }

    public Sensor create(Sensor sensorToCreate) throws FunctionalException {
        sensorToCreate.setEnabled(true);
        SensorRules.validate(sensorToCreate);

        Optional<Sensor> existingSensorWithCode = sensorRepository.findByCode(sensorToCreate.getCode());
        Validate.isFalse(existingSensorWithCode.isPresent(), SensorErrorCode.DUPLICATED_CODE);

        return sensorRepository.save(sensorToCreate);
    }

    public Sensor update(Sensor sensorToUpdate) throws FunctionalException {
        Sensor existingSensor = findByCode(sensorToUpdate.getCode());

        existingSensor.setName(sensorToUpdate.getName());
        existingSensor.setDescription(sensorToUpdate.getDescription());
        existingSensor.setDeviceAddress(sensorToUpdate.getDeviceAddress());

        existingSensor.setOnBattery(sensorToUpdate.isOnBattery());
        existingSensor.setBatteryVoltageThreshold(sensorToUpdate.getBatteryVoltageThreshold());
        existingSensor.setBatteryLevelThreshold(sensorToUpdate.getBatteryLevelThreshold());

        SensorRules.validate(existingSensor);

        return sensorRepository.save(existingSensor);
    }

    public void setSensorEnabled(final String code, final boolean enabled) throws FunctionalException {
        Sensor sensor = this.findByCode(code);

        sensor.setEnabled(enabled);

        sensorRepository.save(sensor);
    }

    public void pushData(final String sensorCode, final SensorData sensorData) throws FunctionalException {
        Sensor sensor = findByCode(sensorCode);
        Validate.isTrue(sensor.isEnabled(), SensorErrorCode.DISABLED_SENSOR);

        // Store new data
        sensorData.setSensorId(sensor.getId());
        sensorData.setReceptionTime(DateUtils.getDateTimeUtc());

        SensorRules.validateData(sensorData);

        SensorData savedSensorData = sensorDataRepository.save(sensorData);

        if (sensorData.isReachable()) {
            sensor.setLastData(savedSensorData);
        }
        sensor.setUnreachableWarning(!sensorData.isReachable());

        if (sensor.isOnBattery() && sensorData.getBatteryVoltage() != null) {
            sensor.setLastBatteryValue(sensorData.getBatteryVoltage());
            sensor.setBatteryWarning(SensorRules.isBatteryLow(sensor, sensorData));
        }

        sensorRepository.save(sensor);
    }

    public List<SensorData> findDataBetween(String sensorId, LocalDateTime minDate, LocalDateTime maxDate) {
        return sensorDataRepository.findSensorData(sensorId, minDate, maxDate);
    }
}
