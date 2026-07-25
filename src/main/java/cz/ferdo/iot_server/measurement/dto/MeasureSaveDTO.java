package cz.ferdo.iot_server.measurement.dto;

public record MeasureSaveDTO(
        String deviceName,
        double value
) {
}
