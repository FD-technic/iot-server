package cz.ferdo.iot_server.measurement.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MeasurementDTO(
        double value,
        LocalDateTime timeStamp
) {}
