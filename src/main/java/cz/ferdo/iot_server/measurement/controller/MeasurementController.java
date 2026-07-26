package cz.ferdo.iot_server.measurement.controller;

import cz.ferdo.iot_server.measurement.dto.MeasureSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import cz.ferdo.iot_server.measurement.query.MeasurementQuery;
import cz.ferdo.iot_server.measurement.service.MeasurementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping
    public MeasurementDTO addMeasure(@RequestBody MeasureSaveDTO measureSaveDTO) {
        return measurementService.add(measureSaveDTO);
    }

    @GetMapping()
    public List<MeasurementDTO> findByQuery(@ModelAttribute MeasurementQuery query) {
        return measurementService.findByQuery(query);
    }
}
