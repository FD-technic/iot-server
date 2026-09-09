package cz.ferdo.iot_server.measurement.dto;

import java.util.List;

public record MeasurementBatchListDTO(
        List<MeasurementBatchDTO> batchList
) {}
