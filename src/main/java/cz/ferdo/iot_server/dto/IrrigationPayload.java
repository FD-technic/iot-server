package cz.ferdo.iot_server.dto;

public record IrrigationPayload(
        double mainTemperature,
        double inputTemperature,
        double outputTemperature
) {
}
