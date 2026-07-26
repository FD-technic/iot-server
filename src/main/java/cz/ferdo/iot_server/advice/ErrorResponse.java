package cz.ferdo.iot_server.advice;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timeStamp
) {
}
