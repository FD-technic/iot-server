package cz.ferdo.iot_server.devices.controller;

import cz.ferdo.iot_server.charts.dto.SensorDTO;
import cz.ferdo.iot_server.charts.dto.SensorMapDTO;
import cz.ferdo.iot_server.commands.command.dto.CommandResponse;
import cz.ferdo.iot_server.devices.dto.*;
import cz.ferdo.iot_server.devices.service.DeviceService;
import cz.ferdo.iot_server.measurement.service.MeasurementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final MeasurementService measurementService;

    public DeviceController(DeviceService deviceService, MeasurementService measurementService) {
        this.deviceService = deviceService;
        this.measurementService = measurementService;
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
        return deviceService.findByName(name);
    }

    @PostMapping("/command")
    public CommandResponse createResponse(@RequestBody DeviceMessageDTO message) {
        return deviceService.createResponse(message);
    }

    @GetMapping("/sensors")
    public List<SensorsOfDeviceDTO> getAllSensors() {
        return deviceService.returnSensors();
    }

    @PostMapping("/status")
    public void setDeviceStatus(@RequestBody DeviceStatusDTO deviceStatusDTO) {
        measurementService.setStatus(deviceStatusDTO);
    }
}
