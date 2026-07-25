package cz.ferdo.iot_server.measurement.repository;

import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {

    List<MeasurementEntity> findByDeviceId(Long deviceId);


    List<MeasurementEntity> findByDeviceIdOrderByTimeStamp(Long deviceId);
}
