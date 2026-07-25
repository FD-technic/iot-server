package cz.ferdo.iot_server.devices.controller;

import cz.ferdo.iot_server.commands.command.dto.CommandResponse;
import cz.ferdo.iot_server.devices.dto.DeviceDTO;
import cz.ferdo.iot_server.devices.dto.DeviceMessageDTO;
import cz.ferdo.iot_server.commands.command.dto.HealthResponse;
import cz.ferdo.iot_server.devices.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                "V0.0.1",
                Instant.now()
        );
    }

    @PostMapping("/device")
    public DeviceDTO createDevice(@RequestBody DeviceDTO deviceDTO) {
        System.out.print("DEVICE: " );
        System.out.println(deviceDTO.getFirmwareVersion());

        return deviceService.add(deviceDTO);
    }

    @GetMapping("/device")
    public List<DeviceDTO> getAllDevices() {
        return deviceService.findAll();
    }

    @GetMapping("/device/{id}")
    public DeviceDTO getDevice(@PathVariable Long id) {
        return deviceService.findById(id);
    }

    @PostMapping("/device/command")
    public CommandResponse createResponse(@RequestBody DeviceMessageDTO message) {

        System.out.println(message);

        return deviceService.createResponse(message);
    }

}
