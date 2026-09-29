package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.measurement.dto.HeatingMeasurementsDTO;

public record ServerResponseDTO(
    ResponseToDeviceDTO responseToDevice,
    HeatingMeasurementsDTO heatingMeasurements,
    DeviceStatusDTO deviceStatusDTO
)
{}
