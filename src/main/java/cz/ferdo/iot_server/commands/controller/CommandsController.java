package cz.ferdo.iot_server.commands.controller;

import cz.ferdo.iot_server.commands.dto.ManualControlDTO;
import cz.ferdo.iot_server.commands.dto.TargetValuesDTO;
import cz.ferdo.iot_server.devices.enums.HeatingMode;
import cz.ferdo.iot_server.measurement.service.MeasurementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/command")
public class CommandsController {

    private final MeasurementService measurementService;

    public CommandsController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/targets")
    public TargetValuesDTO setTargets(@RequestBody TargetValuesDTO targetValuesDTO) {
        return measurementService.setTargets(targetValuesDTO);
    }

    @PostMapping("/manual")
    public ManualControlDTO setManual(@RequestBody ManualControlDTO manualControlDTO) {
        return measurementService.setCommands(manualControlDTO);
    }

    @PostMapping("/mode")
    public String setHeatingMode(@RequestParam HeatingMode heatingMode) {
        return measurementService.setHeatingMode(heatingMode);
    }

    @GetMapping("/targets")
    public TargetValuesDTO getTargets() {
        return measurementService.getTargets();
    }
}
