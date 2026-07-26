package cz.ferdo.iot_server.measurement.repository;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {

    List<MeasurementEntity> findByDevice(DeviceEntity device);


    List<MeasurementEntity> findByDeviceAndTimeStampAfter(DeviceEntity device, LocalDateTime dateFrom);

}
