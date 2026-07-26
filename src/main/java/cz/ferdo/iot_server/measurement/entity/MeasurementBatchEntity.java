package cz.ferdo.iot_server.measurement.entity;

import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "measurement_batch")
public class MeasurementBatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity device;

    @OneToMany(
            mappedBy = "batch",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MeasurementEntity> measurements = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    // === Getter / Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public List<MeasurementEntity> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementEntity> measurements) {
        this.measurements .clear();

        for (MeasurementEntity measurement : measurements) {
            addMeasurement(measurement);
        }
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    /* === Method === */

    private void addMeasurement(MeasurementEntity measurement) {
        measurements.add(measurement);
        measurement.setBatch(this);
    }
}
