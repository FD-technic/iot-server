package cz.ferdo.iot_server.dto;

public record IrrigationResponse(
        double setTemperature,
        boolean pump
) {
}
