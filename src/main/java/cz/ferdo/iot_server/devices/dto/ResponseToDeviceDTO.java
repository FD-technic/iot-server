package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.commands.dto.ManualControlDTO;
import cz.ferdo.iot_server.commands.dto.TargetValuesDTO;
import cz.ferdo.iot_server.devices.enums.HeatingMode;

public class ResponseToDeviceDTO {
    private String deviceName;
    private TemperatureResponseDTO temperatures = new TemperatureResponseDTO();
    private TargetValuesDTO targets = new  TargetValuesDTO();
    private ManualControlDTO manual = new ManualControlDTO();
    private HeatingMode heatingMode = HeatingMode.OFF;

    public ResponseToDeviceDTO(String deviceName ) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public TemperatureResponseDTO getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(TemperatureResponseDTO temperatures) {
        this.temperatures = temperatures;
    }

    public TargetValuesDTO getTargets() {
        return targets;
    }

    public void setTargets(TargetValuesDTO targets) {
        this.targets = targets;
    }

    public HeatingMode getHeatingMode() {
        return heatingMode;
    }

    public void setHeatingMode(HeatingMode heatingMode) {
        this.heatingMode = heatingMode;
    }

    public ManualControlDTO getManual() {
        return manual;
    }

    public void setManual(ManualControlDTO manual) {
        this.manual = manual;
    }
}
