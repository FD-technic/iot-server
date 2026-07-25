package cz.ferdo.iot_server.commands.command.dto;

public record IrrigationResponse(
        double setTemperature,
        boolean pump
) {
}
