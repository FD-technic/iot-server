package cz.ferdo.iot_server.measurement.dto;

import java.util.List;

public record MeasurementSaveDTO(
        String deviceName,
        List<MeasurementValueDTO>  measurements
) {
}
