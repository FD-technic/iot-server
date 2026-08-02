package cz.ferdo.iot_server.charts.dto;

import cz.ferdo.iot_server.measurement.enums.MeasurementType;

public record SensorDTO(
        String deviceName,
        String sensorName
) {
}
