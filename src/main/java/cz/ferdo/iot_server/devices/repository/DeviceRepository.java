package cz.ferdo.iot_server.devices.repository;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Optional<DeviceEntity> findById(Long deviceId);
    Optional<DeviceEntity> findByDeviceName(String deviceName);
    boolean existsByDeviceName(String deviceName);
    List<DeviceEntity> findAll();
}
