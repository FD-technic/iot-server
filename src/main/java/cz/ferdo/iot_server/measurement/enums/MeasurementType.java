package cz.ferdo.iot_server.measurement.enums;

public enum MeasurementType {
    PRESSURE("hPa"),
    TEMPERATURE("°C"),
    HUMIDITY("%");

    private final String unit;

    MeasurementType(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
