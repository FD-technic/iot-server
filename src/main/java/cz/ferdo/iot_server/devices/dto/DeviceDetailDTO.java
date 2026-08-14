package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.charts.dto.SensorMapDTO;

import java.util.List;

public record DeviceDetailDTO(
    DeviceDTO device,
    List<SensorMapDTO> sensors
)
{
}
