package cz.ferdo.iot_server.charts.service;

import cz.ferdo.iot_server.charts.dto.*;
import cz.ferdo.iot_server.charts.query.ChartQuery;
import cz.ferdo.iot_server.core.PeriodService;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.devices.repository.DeviceRepository;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchListDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementValueDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
import cz.ferdo.iot_server.measurement.enums.Period;
import cz.ferdo.iot_server.measurement.mapper.MeasurementMapper;
import cz.ferdo.iot_server.measurement.repository.MeasurementRepository;
import cz.ferdo.iot_server.measurement.service.MeasurementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChartServiceImpl implements ChartService {
    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final MeasurementService measurementService;
    private final DeviceRepository deviceRepository;
    private final PeriodService periodService;

    public ChartServiceImpl(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper, MeasurementService measurementService, DeviceRepository deviceRepository, PeriodService periodService) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
        this.measurementService = measurementService;
        this.deviceRepository = deviceRepository;
        this.periodService = periodService;
    }

    @Override
    public List<ChartSeriesDTO> getChartSeriesByQuery(ChartQuery query) {

        List<ChartSeriesDTO> chartSeriesList = new ArrayList<>();

        List<ChartMeasurementProjection> projections = measurementService.findChartPointsByQuery(query);

        for (ChartMeasurementProjection projection : projections) {
            boolean newSensor = true;
            MeasurementDTO chartPoint = new MeasurementDTO(projection.value(), projection.timeStamp());

            for(ChartSeriesDTO chartSeriesDTO: chartSeriesList) {
                if ( chartSeriesDTO.sensor().sensorName().equals(projection.sensorName())
                        && chartSeriesDTO.sensor().deviceName().equals(projection.deviceName())) {
                    chartSeriesDTO.points().add(chartPoint);
                    newSensor = false;
                    break;
                }
            }
            if ( newSensor ) {
                SensorDTO sensor = new SensorDTO(projection.deviceName(), projection.sensorName());
                List<MeasurementDTO> points = new ArrayList<>();
                points.add(chartPoint);
                chartSeriesList.add(new ChartSeriesDTO(sensor, projection.type(), projection.type().getUnit(), points));
            }
        }

        return reduceChartSeries(chartSeriesList, query.period());
    }

// === Private ===

    private List<ChartSeriesDTO> reduceChartSeries(List<ChartSeriesDTO> sourceList, Period period) {

        List<ChartSeriesDTO> reducedSeries= new ArrayList<>();

        for (ChartSeriesDTO item : sourceList) {

            List<MeasurementDTO> reducedPoints = reduce(item.points(), period);

            reducedSeries.add(new ChartSeriesDTO(item.sensor(), item.type(), item.type().getUnit(), reducedPoints));
        }

        return reducedSeries;
    }

    private List<MeasurementDTO> reduce(List<MeasurementDTO> sourceList, Period period) {
        int step = switch (period) {
            case DAY -> 3;
            case WEEK -> 9;
            case MONTH -> 35;
            case YEAR -> 350;
            default -> 500;
        };

        int count = 0;

        List<MeasurementDTO> measurementList = new ArrayList<>();
        List<MeasurementDTO> blockMeasurementList = new ArrayList<>();
        for (MeasurementDTO measurement : sourceList) {
            if (count == 0 ) {
                measurementList.add(measurement);
            }
            blockMeasurementList.add(measurement);
            count++;
            if (count > 0 && count % step == 0) {
                measurementList.add(nextPoint(blockMeasurementList, measurementList));
                blockMeasurementList = new ArrayList<>();
            }
        }
        if ( !blockMeasurementList.isEmpty() ) {
            measurementList.add(nextPoint(blockMeasurementList, measurementList));
        }

        return measurementList;
    }

    private MeasurementDTO nextPoint(List<MeasurementDTO> measurementList, List<MeasurementDTO> blockMeasurementList) {
        MeasurementDTO point;
        double average = blockMeasurementList.stream()
                .mapToDouble((MeasurementDTO::value))
                .average()
                .orElse(0);

        if (average <= measurementList.getLast().value()) {
            point = blockMeasurementList.stream()
                    .min(Comparator.comparingDouble(MeasurementDTO::value))
                    .orElseThrow();
        } else {
            point = blockMeasurementList.stream()
                    .max(Comparator.comparingDouble(MeasurementDTO::value))
                    .orElseThrow();
        }

        return point;
    }

    // sensors and measurements
    private Map<SensorMapDTO, List<MeasurementDTO>> getMeasurementsBySensor(ChartQuery query) {


        LocalDateTime dateFrom = periodService.findDateFrom(query.period());

        // Zjistí zarízení a senzory v query
        Map<String, List<String>> sensorsMap = getSensorsByDevice(query.sensors());

        // měření za období pro jednotlivá zařízení
        Map<String, MeasurementBatchListDTO> batchListMap = getMeasurementsByDevice(sensorsMap, dateFrom);


        return getMeasurementsBySensor(sensorsMap, batchListMap);
    }


    private Map<String, List<String>> getSensorsByDevice(List<SensorDTO> sensors) {

        // Přidá do mapy všechna zařízení a senzory dle query
        Map<String, List<String>> sensorsMap = new HashMap<>();
        for (SensorDTO sensor : sensors) {

            sensorsMap
                    .computeIfAbsent(sensor.deviceName(), key -> new ArrayList<>())
                    .add(sensor.sensorName());
        }
        return sensorsMap;
    }

    private Map<String, MeasurementBatchListDTO> getMeasurementsByDevice(Map<String, List<String>> sensorsMap, LocalDateTime dateFrom) {

        Map<String, MeasurementBatchListDTO> batchListMap = new HashMap<>();

        for (String key : sensorsMap.keySet()) {
            DeviceEntity device = deviceRepository.findByDeviceName(key)
                    .orElseThrow();

            List<MeasurementBatchEntity> entity = measurementRepository.findByDeviceAndTimeStampAfterOrderByTimeStamp(device, dateFrom);

            MeasurementBatchListDTO batchList = new MeasurementBatchListDTO(
                    entity.stream()
                            .map(measurementMapper::toDTO)
                            .toList()
            );
            batchListMap.put(key, batchList);
        }
        return batchListMap;
    }

    private Map<SensorMapDTO, List<MeasurementDTO>> getMeasurementsBySensor(Map<String, List<String>> sensorsMap, Map<String, MeasurementBatchListDTO> batchListMap) {

        Map<SensorMapDTO, List<MeasurementDTO>> chartLinesMap = new HashMap<>();

        // prochází jednotlivé BatchListy podle nameDevice
        for (String key : batchListMap.keySet()) {
            MeasurementBatchListDTO batchList = batchListMap.get(key);

            // Projde každý záznam
            for (MeasurementBatchDTO batch : batchList.batchList()) {

                LocalDateTime time = batch.timeStamp();

                for (MeasurementValueDTO value : batch.measurements()) {

                        if (sensorsMap.get(key).contains(value.sensorName())) {
                            chartLinesMap
                                    .computeIfAbsent(
                                            new SensorMapDTO(
                                                    new SensorDTO(key, value.sensorName()), value.type()),
                                            lineKey -> new ArrayList<>())
                                    .add(new MeasurementDTO(value.value(), time));
                        }
                    }

            }
        }
        return chartLinesMap;
    }
}