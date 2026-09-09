package cz.ferdo.iot_server.charts.dto;

import cz.ferdo.iot_server.measurement.enums.MeasurementType;

import java.time.LocalDateTime;

public record ChartMeasurementProjection(
        String deviceName,
        String sensorName,
        MeasurementType type,
        double value,
        LocalDateTime timeStamp
) {
}
