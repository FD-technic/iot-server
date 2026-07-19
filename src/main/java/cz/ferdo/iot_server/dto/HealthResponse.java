package cz.ferdo.iot_server.dto;

import java.time.Instant;

public record HealthResponse(
        String status,
        String version,
        Instant timestamp
) {
}
