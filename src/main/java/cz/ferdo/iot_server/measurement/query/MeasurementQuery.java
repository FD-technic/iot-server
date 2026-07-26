package cz.ferdo.iot_server.measurement.query;

import cz.ferdo.iot_server.measurement.enums.Period;

public record MeasurementQuery(
        String deviceName,
        Period period
) {

}
