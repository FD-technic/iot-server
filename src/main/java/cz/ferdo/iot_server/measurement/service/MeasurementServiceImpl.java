package cz.ferdo.iot_server.measurement.service;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.devices.repository.DeviceRepository;
import cz.ferdo.iot_server.measurement.dto.MeasureSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import cz.ferdo.iot_server.measurement.mapper.MeasurementMapper;
import cz.ferdo.iot_server.measurement.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final DeviceRepository deviceRepository;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper, DeviceRepository deviceRepository) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public MeasurementDTO add(MeasureSaveDTO measureDTO) {
        DeviceEntity device = deviceRepository.findByDeviceName(measureDTO.deviceName())
                .orElseThrow();

        MeasurementEntity measurement = measurementMapper.save(measureDTO, device);
        measurement.setTimeStamp(LocalDateTime.now());
        MeasurementEntity saved = measurementRepository.save(measurement);
        return measurementMapper.toDTO(saved);
    }

    @Override
    public List<MeasurementDTO> findByDevice(Long deviceId) {
        List<MeasurementEntity> measurements = measurementRepository.findByDeviceId(deviceId);
        return measurements.stream()
                .map(measurementMapper::toDTO)
                .toList();
    }
    //List<Measurement> findByQuery(MeasurementQuery query);

    // === Private ===


}

