package cz.ferdo.iot_server.measurement.mapper;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.measurement.dto.MeasureSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {
    public MeasurementDTO toDTO(MeasurementEntity entity) {
        return new MeasurementDTO(
                entity.getId(),
                entity.getDevice().getId(),
                entity.getValue(),
                entity.getTimeStamp()
        );
    }

    public MeasurementEntity toEntity(MeasurementDTO dto, DeviceEntity deviceEntity) {
        MeasurementEntity measurementEntity = new MeasurementEntity();
        measurementEntity.setId(dto.id());
        measurementEntity.setDevice(deviceEntity);
        measurementEntity.setValue(dto.value());
        measurementEntity.setTimeStamp(dto.timeStamp());

        return measurementEntity;
    }

    public MeasurementEntity save(MeasureSaveDTO dto, DeviceEntity deviceEntity) {
        MeasurementEntity measurementEntity = new MeasurementEntity();
        measurementEntity.setDevice(deviceEntity);
        measurementEntity.setValue(dto.value());

        return measurementEntity;
    }
}
