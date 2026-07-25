package cz.ferdo.iot_server.measurement.service;

import cz.ferdo.iot_server.measurement.dto.MeasureSaveDTO;
import cz.ferdo.iot_server.measurement.dto.MeasurementDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeasurementService {

    MeasurementDTO add(MeasureSaveDTO measureSaveDTO);
    List<MeasurementDTO> findByDevice(Long deviceId);
    //List<Measurement> findByQuery(MeasurementQuery query);
}

