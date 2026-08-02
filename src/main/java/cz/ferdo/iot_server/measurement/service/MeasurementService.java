package cz.ferdo.iot_server.measurement.service;

import cz.ferdo.iot_server.measurement.dto.MeasurementSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementBatchDTO;
import cz.ferdo.iot_server.measurement.query.MeasurementQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeasurementService {

    MeasurementBatchDTO add(MeasurementBatchDTO measurementDTO);
    List<MeasurementBatchDTO> findByQuery(MeasurementQuery query);
}

