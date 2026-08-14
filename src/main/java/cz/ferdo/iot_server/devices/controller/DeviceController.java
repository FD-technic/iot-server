package cz.ferdo.iot_server.devices.controller;

import cz.ferdo.iot_server.charts.dto.SensorDTO;
import cz.ferdo.iot_server.charts.dto.SensorMapDTO;
import cz.ferdo.iot_server.commands.command.dto.CommandResponse;
import cz.ferdo.iot_server.devices.dto.DeviceDTO;
import cz.ferdo.iot_server.devices.dto.DeviceDetailDTO;
import cz.ferdo.iot_server.devices.dto.DeviceMessageDTO;
import cz.ferdo.iot_server.devices.dto.SensorsOfDeviceDTO;
import cz.ferdo.iot_server.devices.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public DeviceDTO createDevice(@RequestBody DeviceDTO deviceDTO) {
        return deviceService.add(deviceDTO);
    }

    @GetMapping
    public List<DeviceDTO> getAllDevices() {
        return deviceService.findAll();
    }

    @GetMapping("/{name}")
    public DeviceDTO getDevice(@PathVariable String name) {
        System.out.println("Controller: " + name);
        return deviceService.findByName(name);
    }

    @PostMapping("/command")
    public CommandResponse createResponse(@RequestBody DeviceMessageDTO message) {

        return deviceService.createResponse(message);
    }

    @GetMapping("/sensors")
    public List<SensorsOfDeviceDTO> getAllSensors() {

        System.out.println("Return all sensors");
        return deviceService.returnSensors();
    }
}
