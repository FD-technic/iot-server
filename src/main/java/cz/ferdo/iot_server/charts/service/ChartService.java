package cz.ferdo.iot_server.charts.service;

import cz.ferdo.iot_server.charts.dto.ChartSeriesDTO;
import cz.ferdo.iot_server.charts.query.ChartQuery;

import java.util.List;

public interface ChartService {
    List<ChartSeriesDTO> getChartSeries(ChartQuery query);
}
