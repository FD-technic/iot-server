package cz.ferdo.iot_server.devices.dto;

import cz.ferdo.iot_server.core.enums.DeviceType;

public class DeviceDTO {

    private Long id;
    private String name;
    private String description;
    private String firmwareVersion;
    private DeviceType deviceType;

    public DeviceDTO(Long id, String name, String description, String firmwareVersion, DeviceType deviceType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firmwareVersion = firmwareVersion;
        this.deviceType = deviceType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
