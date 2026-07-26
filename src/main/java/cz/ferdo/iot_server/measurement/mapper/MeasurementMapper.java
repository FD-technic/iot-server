package cz.ferdo.iot_server.measurement.mapper;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.measurement.dto.MeasurementValueDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {
    public MeasurementBatchDTO toDTO(MeasurementBatchEntity entity) {
        return new MeasurementBatchDTO(
                entity.getId(),
                entity.getDevice().getDeviceName(),
                entity.getMeasurements().stream().map(this::toDTO).toList(),
                entity.getTimeStamp()
        );
    }

    public MeasurementBatchEntity toEntity(MeasurementBatchDTO dto, DeviceEntity deviceEntity) {
        MeasurementBatchEntity measurementBatchEntity = new MeasurementBatchEntity();
        measurementBatchEntity.setId(dto.id());
        measurementBatchEntity.setDevice(deviceEntity);
        measurementBatchEntity.setMeasurements(
                dto.measurements()
                        .stream()
                        .map(this::toEntity)
                        .toList());
        measurementBatchEntity.setTimeStamp(dto.timeStamp());

        return measurementBatchEntity;
    }

    public MeasurementBatchEntity save(MeasurementBatchDTO dto, DeviceEntity deviceEntity) {
        MeasurementBatchEntity measurementBatchEntity = new MeasurementBatchEntity();
        measurementBatchEntity.setDevice(deviceEntity);
        measurementBatchEntity.setMeasurements(
                dto.measurements()
                        .stream()
                        .map(this::toEntity).toList());

        return measurementBatchEntity;
    }

    public MeasurementEntity toEntity(MeasurementValueDTO valueDTO) {
        MeasurementEntity entity = new MeasurementEntity();
        entity.setSensorName(valueDTO.sensorName());
        entity.setMeasurementType(valueDTO.type());
        entity.setSensorValue(valueDTO.value());

        return entity;
    }

    public MeasurementValueDTO toDTO(MeasurementEntity measurementEntity) {
        return new MeasurementValueDTO(
                measurementEntity.getSensorName(),
                measurementEntity.getMeasurementType(),
                measurementEntity.getSensorValue()
        );
    }
}
