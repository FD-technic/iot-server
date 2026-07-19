package cz.ferdo.iot_server.service;

import cz.ferdo.iot_server.dto.CommandResponse;
import cz.ferdo.iot_server.dto.DeviceMessage;
import org.springframework.stereotype.Service;

@Service
public interface EspService {

    CommandResponse createResponse(DeviceMessage message);
}
