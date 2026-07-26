package cz.ferdo.iot_server.measurement.dto;

import cz.ferdo.iot_server.measurement.enums.MeasurementType;

public record MeasurementValueDTO(
        String sensorName,
        MeasurementType type,
        double value
) {
}
