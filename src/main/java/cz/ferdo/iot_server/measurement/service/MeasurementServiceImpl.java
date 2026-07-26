package cz.ferdo.iot_server.measurement.service;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.devices.repository.DeviceRepository;
import cz.ferdo.iot_server.measurement.dto.MeasureSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import cz.ferdo.iot_server.measurement.enums.Period;
import cz.ferdo.iot_server.measurement.mapper.MeasurementMapper;
import cz.ferdo.iot_server.measurement.query.MeasurementQuery;
import cz.ferdo.iot_server.measurement.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

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
    public List<MeasurementDTO> findByQuery(MeasurementQuery query) {

        DeviceEntity deviceEntity = fetchDeviceByDeviceName(query.deviceName());

        if (query.period() == Period.ALL) {
            return streamToDTO(measurementRepository.findByDevice(deviceEntity));
        }

        LocalDateTime dateFrom = switch(query.period()) {
            case DAY -> now().minusDays(1);
            case WEEK -> now().minusWeeks(1);
            case MONTH -> now().minusMonths(1);
            case QUARTER -> now().minusMonths(3);
            case HALF -> now().minusMonths(6);
            case YEAR -> now().minusYears(1);
            case ALL -> throw new IllegalStateException("ALL should be handled before switch");
        };

        return streamToDTO(measurementRepository.findByDeviceAndTimeStampAfter(deviceEntity, dateFrom));
    }

    // === Private ===

    private DeviceEntity fetchDeviceByDeviceName(String deviceName) {
        return deviceRepository.findByDeviceName(deviceName)
                .orElseThrow();
    }

    private List<MeasurementDTO> streamToDTO(List<MeasurementEntity> measurements) {
        return measurements.stream().map(measurementMapper::toDTO).toList();
    }
}

