package cz.ferdo.iot_server.advice;

public class DeviceAlreadyExistsException extends RuntimeException {

    public DeviceAlreadyExistsException(String deviceName) {
        super("Device with name: '" + deviceName + "' already exists.");
    }
}
