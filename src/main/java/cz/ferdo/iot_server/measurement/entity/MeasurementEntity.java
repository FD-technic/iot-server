package cz.ferdo.iot_server.measurement.entity;

import cz.ferdo.iot_server.measurement.enums.MeasurementType;
import jakarta.persistence.*;


@Entity
@Table(name = "measurement")
public class MeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private MeasurementBatchEntity batch;

    @Column(nullable = false)
    private String sensorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementType measurementType;

    @Column(nullable = false)
    private double sensorValue;

    /* === Getter Setter === */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasurementBatchEntity getBatch() {
        return batch;
    }

    public void setBatch(MeasurementBatchEntity batch) {
        this.batch = batch;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(double sensorValue) {
        this.sensorValue = sensorValue;
    }
}
