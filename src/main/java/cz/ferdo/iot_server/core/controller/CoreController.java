package cz.ferdo.iot_server.core.controller;

import cz.ferdo.iot_server.commands.command.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class CoreController {

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                "V0.1.1",
                Instant.now()
        );
    }
}
