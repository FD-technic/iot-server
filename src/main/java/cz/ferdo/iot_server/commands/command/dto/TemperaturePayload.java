package cz.ferdo.iot_server.commands.command.dto;

public record TemperaturePayload(
        double temperature,
        String deviceId
) {
}
