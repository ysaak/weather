package ysaak.weather.dao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ysaak.weather.data.SensorData;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorDataRepository extends CrudRepository<SensorData, String> {
    @Query("SELECT sd " +
            "FROM SensorData AS sd " +
            "WHERE sd.sensorId = :sensorId " +
            "AND sd.receptionTime >= :startReceptionTime " +
            "AND sd.receptionTime <= :endReceptionTime " +
            "ORDER BY sd.receptionTime DESC")
    List<SensorData> findSensorData(@Param("sensorId") String sensorId,
                                    @Param("startReceptionTime") LocalDateTime startReceptionTime,
                                    @Param("endReceptionTime") LocalDateTime endReceptionTime);
}
