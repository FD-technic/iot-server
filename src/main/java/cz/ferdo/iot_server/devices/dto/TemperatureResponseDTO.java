package cz.ferdo.iot_server.devices.dto;

public class TemperatureResponseDTO {

        private double outdoor = 15;
        private double indoor = 20;

    public TemperatureResponseDTO() {}

    public TemperatureResponseDTO(double outdoor, double indoor) {
        this.outdoor = outdoor;
        this.indoor = indoor;
    }

    public double getOutdoor() { return outdoor; }

    public void setOutdoor(double outdoor) {
        this.outdoor = outdoor;
    }

    public double getIndoor() {
        return indoor;
    }

    public void setIndoor(double indoor) {
        this.indoor = indoor;
    }
}
