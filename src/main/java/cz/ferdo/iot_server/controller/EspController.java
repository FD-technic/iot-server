package cz.ferdo.iot_server.controller;

import cz.ferdo.iot_server.dto.CommandResponse;
import cz.ferdo.iot_server.dto.DeviceMessage;
import cz.ferdo.iot_server.dto.HealthResponse;
import cz.ferdo.iot_server.service.EspService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class EspController {

    private final EspService espService;

    public EspController(EspService espService) {
        this.espService = espService;
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
    public CommandResponse createResponse(@RequestBody DeviceMessage message) {

        System.out.println(message);

        return espService.createResponse(message);
    }
}
