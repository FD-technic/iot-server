package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.charts.dto.SensorMapDTO;

import java.util.List;

public record SensorsOfDeviceDTO(
    String deviceName,
    List<String> sensorNames
)
{
}
