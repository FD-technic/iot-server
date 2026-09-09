package cz.ferdo.iot_server.charts.dto;

import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.enums.MeasurementType;

import java.util.List;

public record ChartSeriesDTO(
        SensorDTO sensor,
        MeasurementType type,
        String unit,
        List<MeasurementDTO> points
) {
}

