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

    @PostMapping("/devices")
    public DeviceDTO createDevice(@RequestBody DeviceDTO deviceDTO) {
        return deviceService.add(deviceDTO);
    }

    @GetMapping("/devices")
    public List<DeviceDTO> getAllDevices() {
        return deviceService.findAll();
    }

    @GetMapping("/devices/{name}")
    public DeviceDTO getDevice(@PathVariable String name) {
        System.out.println("Controller: " + name);
        return deviceService.findByName(name);
    }

    @PostMapping("/devices/command")
    public CommandResponse createResponse(@RequestBody DeviceMessageDTO message) {

        System.out.println(message);

        return deviceService.createResponse(message);
    }
}
