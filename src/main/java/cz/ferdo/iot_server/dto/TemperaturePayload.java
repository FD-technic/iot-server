package cz.ferdo.iot_server.dto;

public record TemperaturePayload(
        double temperature,
        String deviceId
) {
}
