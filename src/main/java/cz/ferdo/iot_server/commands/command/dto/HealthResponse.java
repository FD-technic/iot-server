package cz.ferdo.iot_server.commands.command.dto;

import java.time.Instant;

public record HealthResponse(
        String status,
        String version,
        Instant timestamp
) {
}
