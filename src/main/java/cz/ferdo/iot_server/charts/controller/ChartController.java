package cz.ferdo.iot_server.charts.controller;

import cz.ferdo.iot_server.charts.dto.ChartSeriesDTO;
import cz.ferdo.iot_server.charts.query.ChartQuery;
import cz.ferdo.iot_server.charts.service.ChartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @PostMapping
    public List<ChartSeriesDTO> getChartPoints(@RequestBody ChartQuery query) {
        return chartService.getChartSeries(query);
    }
}
