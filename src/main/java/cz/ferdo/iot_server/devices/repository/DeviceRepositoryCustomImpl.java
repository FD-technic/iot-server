package cz.ferdo.iot_server.devices.repository;

import cz.ferdo.iot_server.charts.dto.SensorDTO;
import cz.ferdo.iot_server.charts.dto.SensorMapDTO;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Path;

import java.util.ArrayList;
import java.util.List;

public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<SensorDTO> findAllSensors() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SensorDTO> cq = cb.createQuery(SensorDTO.class);
        Root<MeasurementBatchEntity> root = cq.from(MeasurementBatchEntity.class);

        Join<MeasurementBatchEntity, DeviceEntity> device = root.join("device");
        Join<MeasurementBatchEntity, MeasurementEntity> measurement = root.join("measurements");

        Path<String> deviceName= device.get("deviceName");
        Path<String> sensorName = measurement.get("sensorName");

        cq.select(
                cb.construct(
                        SensorDTO.class,
                        deviceName,
                        sensorName
                )
        );
        cq.distinct(true);

        TypedQuery<SensorDTO> query = em.createQuery(cq);

        return query.getResultList();
    }

}
