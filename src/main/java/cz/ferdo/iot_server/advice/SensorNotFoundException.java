package cz.ferdo.iot_server.advice;

public class SensorNotFoundException extends RuntimeException {

    public SensorNotFoundException(String deviceName) {
        super("Sensor with name: '" + deviceName + "' not found!");
    }
}
