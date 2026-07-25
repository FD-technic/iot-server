package cz.ferdo.iot_server.commands.command.dto;

public record IrrigationPayload(
        double mainTemperature,
        double inputTemperature,
        double outputTemperature
) {
}
