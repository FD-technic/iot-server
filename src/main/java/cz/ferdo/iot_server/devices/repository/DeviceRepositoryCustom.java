package cz.ferdo.iot_server.devices.repository;

import cz.ferdo.iot_server.charts.dto.SensorDTO;

import java.util.List;

public interface DeviceRepositoryCustom {

    List<SensorDTO> findAllSensors();

}
