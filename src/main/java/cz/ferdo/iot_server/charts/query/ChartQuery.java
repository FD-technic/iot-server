package cz.ferdo.iot_server.charts.query;

import cz.ferdo.iot_server.charts.dto.SensorDTO;
import cz.ferdo.iot_server.measurement.enums.Period;

import java.util.List;

public record ChartQuery(
        List<SensorDTO>  sensors,
        Period period
) {

}
