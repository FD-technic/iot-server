package cz.ferdo.iot_server.charts.service;

import cz.ferdo.iot_server.advice.DeviceNotFoundException;
import cz.ferdo.iot_server.advice.SensorNotFoundException;
import cz.ferdo.iot_server.charts.dto.ChartPointDTO;
import cz.ferdo.iot_server.charts.dto.ChartSeriesDTO;
import cz.ferdo.iot_server.charts.dto.SensorDTO;
import cz.ferdo.iot_server.charts.dto.SensorMapDTO;
import cz.ferdo.iot_server.charts.query.ChartQuery;
import cz.ferdo.iot_server.core.PeriodService;
import cz.ferdo.iot_server.devices.dto.DeviceDTO;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.devices.repository.DeviceRepository;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import cz.ferdo.iot_server.measurement.enums.MeasurementType;
import cz.ferdo.iot_server.measurement.enums.Period;
import cz.ferdo.iot_server.measurement.mapper.MeasurementMapper;
import cz.ferdo.iot_server.measurement.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {
    private final MeasurementRepository measurementRepository;
    private final DeviceRepository deviceRepository;
    private final PeriodService periodService;
    private final MeasurementMapper measurementMapper;

    public ChartServiceImpl(MeasurementRepository measurementRepository, DeviceRepository deviceRepository, PeriodService periodService, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.deviceRepository = deviceRepository;
        this.periodService = periodService;
        this.measurementMapper = measurementMapper;
    }

    @Override
    public List<ChartSeriesDTO> getChartSeries(ChartQuery query) {

        List<ChartSeriesDTO> chartSeriesList = new ArrayList<>();

        Map<SensorMapDTO, List<ChartPointDTO>> map = getSensorsMap(query);

        for (Map.Entry<SensorMapDTO, List<ChartPointDTO>> entry : map.entrySet()) {
            List<ChartPointDTO> chartPoints = entry.getValue();
            chartSeriesList.add(
                    new ChartSeriesDTO(
                            entry.getKey().sensor().sensorName(),
                            entry.getKey().type(),
                            entry.getKey().type().getUnit(),
                            chartPoints));
        }

        return chartSeriesList;
    }

// === Private ===

    private Map<SensorMapDTO, List<ChartPointDTO>> getSensorsMap(ChartQuery query) {

        LocalDateTime dateFrom = periodService.findDateFrom(query.period());

        Map<SensorMapDTO, List<ChartPointDTO>> seriesMap = new HashMap<>();

        // Prochází všechna zařízení dle seznamu v query
        for (SensorDTO sensor : query.sensors()) {

            DeviceEntity device = deviceRepository.findByDeviceName(sensor.deviceName())
                    .orElseThrow(() -> new DeviceNotFoundException(sensor.deviceName()));

            List<MeasurementBatchEntity> batchList = measurementRepository.findByDeviceAndTimeStampAfter(device, dateFrom);

            // Projde záznamy měření na jednom zařízení
            for (MeasurementBatchEntity measurementBatchEntity : batchList) {

                // Vytvoří řadu podle senzoru na zařízení
                for (MeasurementEntity measurementEntity : measurementBatchEntity.getMeasurements()) {

                    SensorMapDTO sensorMap = new SensorMapDTO(sensor, measurementEntity.getMeasurementType());

                    if (measurementEntity.getSensorName().equals(sensor.sensorName())) {

                        ChartPointDTO point = measurementMapper.toChartPointDTO(measurementEntity, measurementBatchEntity);

                        seriesMap.putIfAbsent(sensorMap, new ArrayList<>());
                        seriesMap.get(sensorMap).add(point);
                    }
                }
            }
        }

        return seriesMap;
    }
}