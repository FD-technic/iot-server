package cz.ferdo.iot_server.devices.service;

import cz.ferdo.iot_server.commands.command.dto.CommandResponse;
import cz.ferdo.iot_server.devices.dto.DeviceDTO;
import cz.ferdo.iot_server.devices.dto.DeviceMessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceService {

    DeviceDTO add(DeviceDTO deviceDTO);
    List<DeviceDTO> findAll();
    DeviceDTO findById(Long deviceId);
    DeviceDTO update(Long deviceId, DeviceDTO deviceDTO);
    DeviceDTO delete(Long deviceId);
    CommandResponse createResponse(DeviceMessageDTO message);
}
