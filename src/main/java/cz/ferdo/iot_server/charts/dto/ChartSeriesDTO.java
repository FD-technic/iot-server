package cz.ferdo.iot_server.charts.dto;

import cz.ferdo.iot_server.measurement.enums.MeasurementType;

import java.util.List;

public record ChartSeriesDTO(
        String name,
        MeasurementType type,
        String unit,
        List<ChartPointDTO> points
) {
}
