package cz.ferdo.iot_server.measurement.controller;

import cz.ferdo.iot_server.devices.dto.ResponseToDeviceDTO;
import cz.ferdo.iot_server.devices.dto.ServerResponseDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
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
    public ResponseToDeviceDTO addMeasure(@RequestBody MeasurementBatchDTO measurementDTO) {
        System.out.print("ADD MEASUREMENT: ");
        return measurementService.add(measurementDTO);
    }

    @GetMapping
    public List<MeasurementBatchDTO> findByQuery(@ModelAttribute MeasurementQuery query) {
        return measurementService.findByQuery(query);
    }

    @GetMapping("/response")
    public ServerResponseDTO getResponse() {
        return measurementService.getResponse();
    }

}
