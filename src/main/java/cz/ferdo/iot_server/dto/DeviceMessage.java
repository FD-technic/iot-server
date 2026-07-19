package cz.ferdo.iot_server.dto;

import cz.ferdo.iot_server.enums.DeviceType;
import tools.jackson.databind.JsonNode;

public record DeviceMessage(
        String deviceId,
        DeviceType deviceType,
        String firmware,
        JsonNode payload
) {
}
