package cz.ferdo.iot_server.measurement.repository;

import cz.ferdo.iot_server.charts.dto.ChartMeasurementProjection;
import cz.ferdo.iot_server.charts.dto.SensorDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepositoryCustom {

    List<ChartMeasurementProjection> findChartMeasurements(List<SensorDTO> sensors, LocalDateTime dateFrom);
}
