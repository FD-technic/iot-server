package cz.ferdo.iot_server.measurement.dto;

import java.time.LocalDateTime;

public record MeasurementDTO(
        Long id,
        Long deviceId,
        double value,
        LocalDateTime timeStamp
) {}
