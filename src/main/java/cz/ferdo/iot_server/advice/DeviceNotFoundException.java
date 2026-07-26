package cz.ferdo.iot_server.advice;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(String deviceName) {
        super("Device with name: '" + deviceName + "' not found!");
    }
}
