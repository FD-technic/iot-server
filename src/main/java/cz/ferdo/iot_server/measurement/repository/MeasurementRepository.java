package cz.ferdo.iot_server.measurement.repository;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<MeasurementBatchEntity, Long>, MeasurementRepositoryCustom {

    List<MeasurementBatchEntity> findByDevice(DeviceEntity device);


    List<MeasurementBatchEntity> findByDeviceAndTimeStampAfterOrderByTimeStamp(DeviceEntity device, LocalDateTime dateFrom);

}
