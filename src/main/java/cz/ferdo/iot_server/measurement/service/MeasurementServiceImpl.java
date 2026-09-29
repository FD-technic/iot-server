package cz.ferdo.iot_server.measurement.service;

import cz.ferdo.iot_server.charts.dto.ChartMeasurementProjection;
import cz.ferdo.iot_server.charts.query.ChartQuery;
import cz.ferdo.iot_server.commands.dto.ManualControlDTO;
import cz.ferdo.iot_server.commands.dto.TargetValuesDTO;
import cz.ferdo.iot_server.core.PeriodService;
import cz.ferdo.iot_server.devices.dto.*;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.devices.enums.HeatingMode;
import cz.ferdo.iot_server.devices.enums.ValveDirection;
import cz.ferdo.iot_server.devices.repository.DeviceRepository;
import cz.ferdo.iot_server.measurement.dto.HeatingMeasurementsDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementValueDTO;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
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
    private final PeriodService periodService;

    private final ResponseToDeviceDTO responseToDeviceDTO = new ResponseToDeviceDTO("Heating Controller");
    private volatile HeatingMeasurementsDTO heatingMeasurements;
    private volatile DeviceStatusDTO deviceStatus;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper, DeviceRepository deviceRepository, PeriodService periodService) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
        this.deviceRepository = deviceRepository;
        this.periodService = periodService;
    }

    @Override
    public ResponseToDeviceDTO add(MeasurementBatchDTO measurementDTO) {
        DeviceEntity device = fetchDeviceByDeviceName(measurementDTO.deviceName());

        MeasurementBatchEntity measurementBatch = new MeasurementBatchEntity();
        measurementBatch.setDevice(device);
        measurementBatch.setTimeStamp(now());

        List<MeasurementEntity> measurements = measurementDTO.measurements().stream()
                .map(measurementMapper::toEntity)
                .toList();

        measurementBatch.setMeasurements(measurements);

        measurementRepository.save(measurementBatch);

        if (!measurementDTO.deviceName().equals("Heating Controller")) {
            TemperatureResponseDTO temperatures = responseToDeviceDTO.getTemperatures();
            setTemperatures(temperatures, measurementDTO);
        } else {

            heatingMeasurements = toHeatingMeasurements(measurementDTO);

            if (responseToDeviceDTO.getHeatingMode() == HeatingMode.MANUAL) {
                ResponseToDeviceDTO response = new ResponseToDeviceDTO("NEW");
                response.setDeviceName(responseToDeviceDTO.getDeviceName());
                response.setTemperatures(responseToDeviceDTO.getTemperatures());
                response.setHeatingMode(responseToDeviceDTO.getHeatingMode());
                response.setTargets(responseToDeviceDTO.getTargets());

                ManualControlDTO manualControlDTO = new ManualControlDTO();
                manualControlDTO.setHeatingPump(responseToDeviceDTO.getManual().isHeatingPump());
                manualControlDTO.setWaterHeaterPump(responseToDeviceDTO.getManual().isWaterHeaterPump());
                manualControlDTO.setValveDirection(responseToDeviceDTO.getManual().getValveDirection());
                response.setManual(manualControlDTO);

                responseToDeviceDTO.getManual().setValveDirection(ValveDirection.STOP);
                return response;
            }

            return responseToDeviceDTO;

        }
        return null;
    }

    @Override
    public ServerResponseDTO getResponse() {
        return new ServerResponseDTO(responseToDeviceDTO, heatingMeasurements, deviceStatus);
    }

    @Override
    public TargetValuesDTO setTargets(TargetValuesDTO sourceValues) {
        TargetValuesDTO targetValues = responseToDeviceDTO.getTargets();
        targetValues.setRoom(sourceValues.getRoom());
        targetValues.setWaterHeater(sourceValues.getWaterHeater());
        targetValues.setHeatingHysteresis(sourceValues.getHeatingHysteresis());
        targetValues.setWaterHeatingHysteresis(sourceValues.getWaterHeatingHysteresis());

        responseToDeviceDTO.setTargets(targetValues);

        return responseToDeviceDTO.getTargets();
    }

    @Override
    public TargetValuesDTO getTargets() {
        return responseToDeviceDTO.getTargets();
    }

    @Override
    public String setHeatingMode(HeatingMode heatingMode) {
        responseToDeviceDTO.setHeatingMode(heatingMode);
        HeatingMode mode = responseToDeviceDTO.getHeatingMode();
        if (mode != HeatingMode.MANUAL) {
            ManualControlDTO manual = responseToDeviceDTO.getManual();

            manual.setHeatingPump(false);
            manual.setWaterHeaterPump(false);
            manual.setValveDirection(ValveDirection.STOP);
        }
        String response = responseToDeviceDTO.getHeatingMode().toString();
        return "Heating Mode: " + response;
    }

    @Override
    public ManualControlDTO setCommands(ManualControlDTO sourceCommands) {
        ManualControlDTO commands = responseToDeviceDTO.getManual();
        commands.setHeatingPump(sourceCommands.isHeatingPump());
        commands.setWaterHeaterPump(sourceCommands.isWaterHeaterPump());
        commands.setValveDirection(sourceCommands.getValveDirection());

        responseToDeviceDTO.setManual(commands);

        return responseToDeviceDTO.getManual();
    }

    @Override
    public List<MeasurementBatchDTO> findByQuery(MeasurementQuery query) {

        DeviceEntity deviceEntity = fetchDeviceByDeviceName(query.deviceName());

        if (query.period() == Period.ALL) {
            return streamToDTO(measurementRepository.findByDevice(deviceEntity));
        }

        LocalDateTime dateFrom = periodService.findDateFrom(query.period());

        return streamToDTO(measurementRepository.findByDeviceAndTimeStampAfterOrderByTimeStamp(deviceEntity, dateFrom));
    }

    @Override
    public List<ChartMeasurementProjection> findChartPointsByQuery(ChartQuery query) {

        LocalDateTime dateFrom = periodService.findDateFrom(query.period());

        return measurementRepository.findChartMeasurements(query.sensors(), dateFrom);
    }

    @Override
    public void setStatus(DeviceStatusDTO deviceStatusDTO) {
        this.deviceStatus = deviceStatusDTO;
    }

    // === Private ===

    private DeviceEntity fetchDeviceByDeviceName(String deviceName) {
        return deviceRepository.findByDeviceName(deviceName)
                .orElseThrow();
    }

    private List<MeasurementBatchDTO> streamToDTO(List<MeasurementBatchEntity> measurements) {
        return measurements.stream().map(measurementMapper::toDTO).toList();
    }

    private void setTemperatures(TemperatureResponseDTO temperatures, MeasurementBatchDTO measurementDTO) {
        if (measurementDTO.deviceName().equals("outdoor")) {
            temperatures.setOutdoor(measurementDTO.measurements().stream()
                    .filter(m -> m.sensorName().equals("Temperature"))
                    .findFirst()
                    .map(MeasurementValueDTO::value)
                    .orElse(0.0));
        } else if (measurementDTO.deviceName().equals("study")) {
            temperatures.setIndoor(measurementDTO.measurements().stream()
                    .filter(m -> m.sensorName().equals("Temperature"))
                    .findFirst()
                    .map(MeasurementValueDTO::value)
                    .orElse(0.0));
        }

        responseToDeviceDTO.setTemperatures(temperatures);
    }

    private HeatingMeasurementsDTO toHeatingMeasurements(
            MeasurementBatchDTO batch
    ) {
        return new HeatingMeasurementsDTO(
                getTemperature(batch, "inHeating"),
                getTemperature(batch, "outHeating"),
                getTemperature(batch, "inValve"),
                getTemperature(batch, "waterHeater")
        );
    }

    private double getTemperature(
            MeasurementBatchDTO batch,
            String sensorName
    ) {
        return batch.measurements().stream()
                .filter(m -> m.sensorName().equals(sensorName))
                .findFirst()
                .map(MeasurementValueDTO::value)
                .orElse(Double.NaN);
    }
}

