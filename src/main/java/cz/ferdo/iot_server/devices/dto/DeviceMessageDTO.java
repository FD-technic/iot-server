package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.core.enums.DeviceType;
import tools.jackson.databind.JsonNode;

public record DeviceMessageDTO(
        String deviceId,
        DeviceType deviceType,
        String firmware,
        JsonNode payload
) {
}
