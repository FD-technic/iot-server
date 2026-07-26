package cz.ferdo.iot_server.measurement.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MeasurementBatchDTO(
        Long id,
        String deviceName,
        List<MeasurementValueDTO> measurements,
        LocalDateTime timeStamp
) {}
