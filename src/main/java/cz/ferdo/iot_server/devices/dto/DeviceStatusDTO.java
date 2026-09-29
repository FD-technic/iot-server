package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.devices.enums.HeatingMode;
import cz.ferdo.iot_server.devices.enums.ValveDirection;

public record DeviceStatusDTO(
    String deviceName,
    HeatingMode heatingMode,
    ValveDirection valveDirection,
    boolean heatingPump,
    boolean waterHeaterPump
)
{
}
