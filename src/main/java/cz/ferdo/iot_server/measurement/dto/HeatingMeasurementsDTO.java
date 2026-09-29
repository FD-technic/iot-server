package cz.ferdo.iot_server.measurement.dto;

import java.util.List;

public record HeatingMeasurementsDTO(
        double inHeating,
        double outHeating,
        double inValve,
        double waterHeater
) {
}
