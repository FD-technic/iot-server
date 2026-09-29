package cz.ferdo.iot_server.measurement.service;

import cz.ferdo.iot_server.charts.dto.ChartMeasurementProjection;
import cz.ferdo.iot_server.charts.query.ChartQuery;
import cz.ferdo.iot_server.commands.dto.ManualControlDTO;
import cz.ferdo.iot_server.commands.dto.TargetValuesDTO;
import cz.ferdo.iot_server.devices.dto.DeviceStatusDTO;
import cz.ferdo.iot_server.devices.dto.ResponseToDeviceDTO;
import cz.ferdo.iot_server.devices.dto.ServerResponseDTO;
import cz.ferdo.iot_server.devices.enums.HeatingMode;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
import cz.ferdo.iot_server.measurement.query.MeasurementQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeasurementService {

    ResponseToDeviceDTO add(MeasurementBatchDTO measurementDTO);
    ServerResponseDTO getResponse();
    List<MeasurementBatchDTO> findByQuery(MeasurementQuery query);
    List<ChartMeasurementProjection> findChartPointsByQuery(ChartQuery query);
    TargetValuesDTO setTargets(TargetValuesDTO sourceValues);
    TargetValuesDTO getTargets();
    String setHeatingMode(HeatingMode heatingMode);
    ManualControlDTO setCommands(ManualControlDTO manualControlDTO);
    void setStatus(DeviceStatusDTO deviceStatusDTO);
}

