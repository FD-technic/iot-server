package cz.ferdo.iot_server.measurement.repository;

import cz.ferdo.iot_server.charts.dto.ChartMeasurementProjection;
import cz.ferdo.iot_server.charts.dto.SensorDTO;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementBatchEntity;
import cz.ferdo.iot_server.measurement.entity.MeasurementEntity;
import cz.ferdo.iot_server.measurement.enums.MeasurementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeasurementRepositoryCustomImpl implements MeasurementRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ChartMeasurementProjection> findChartMeasurements(
            List<SensorDTO> sensors,
            LocalDateTime dateFrom
    ) {
        if (sensors.isEmpty()) {
            return List.of();
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChartMeasurementProjection> cq = cb.createQuery(ChartMeasurementProjection.class);

        Root<MeasurementEntity> measurement = cq.from(MeasurementEntity.class);

        Join<MeasurementEntity, MeasurementBatchEntity> batch = measurement.join("batch");
        Join<MeasurementBatchEntity, DeviceEntity> device = batch.join("device");

        Path<String> deviceName = device.get("deviceName");
        Path<String> sensorName = measurement.get("sensorName");
        Path<MeasurementType> type = measurement.get("measurementType");
        Path<Double> value = measurement.get("sensorValue");
        Path<LocalDateTime> timestamp = batch.get("timeStamp");

        List<Predicate> predicates = new ArrayList<>();

        for (SensorDTO sensor : sensors) {
            Predicate sensorPredicate = cb.and(
                cb.equal(deviceName, sensor.deviceName()),
                cb.equal(sensorName, sensor.sensorName())
            );

            predicates.add(sensorPredicate);
        }

        cq.select(
                cb.construct(
                        ChartMeasurementProjection.class,
                        deviceName,
                        sensorName,
                        type,
                        value,
                        timestamp
                )
        );
        cq.where(
                cb.greaterThan(batch.get("timeStamp"), dateFrom),
                cb.or(predicates.toArray(new Predicate[0]))
        );
        cq.orderBy(cb.asc(timestamp));

        return em.createQuery(cq).getResultList();
    }
}
