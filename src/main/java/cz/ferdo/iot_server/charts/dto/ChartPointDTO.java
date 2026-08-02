package cz.ferdo.iot_server.charts.dto;

import java.time.LocalDateTime;

public record ChartPointDTO(
        LocalDateTime dateTime,
        double value
) {
}
