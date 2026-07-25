package cz.ferdo.iot_server.devices.mapper;

import cz.ferdo.iot_server.devices.dto.DeviceDTO;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public DeviceDTO toDTO(DeviceEntity entity) {
        return new DeviceDTO(
                entity.getId(),
                entity.getDeviceName(),
                entity.getDescription(),
                entity.getFirmwareVersion(),
                entity.getDeviceType()
        );
    }

    public DeviceEntity toEntity(DeviceDTO dto) {
        DeviceEntity entity = new DeviceEntity();
        entity.setDeviceName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFirmwareVersion(dto.getFirmwareVersion());
        entity.setDeviceType(dto.getDeviceType());

        return entity;
    }
}
