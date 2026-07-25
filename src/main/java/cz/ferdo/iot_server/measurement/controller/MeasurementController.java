package cz.ferdo.iot_server.measurement.controller;

import cz.ferdo.iot_server.measurement.dto.MeasureSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.service.MeasurementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measure")
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping
    public MeasurementDTO addMeasure(@RequestBody MeasureSaveDTO measureSaveDTO) {
        return measurementService.add(measureSaveDTO);
    }

    @GetMapping("/{deviceId}")
    public List<MeasurementDTO> findByDeviceId(@PathVariable Long deviceId) {
        return measurementService.findByDevice(deviceId);
    }
}
