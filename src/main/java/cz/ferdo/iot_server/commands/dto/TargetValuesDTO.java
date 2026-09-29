package cz.ferdo.iot_server.commands.dto;

public class TargetValuesDTO {

    private double room = 20;
    private double waterHeater = 60;
    private double heatingHysteresis = 3;
    private double waterHeatingHysteresis = 5;

    public TargetValuesDTO() {}

    public TargetValuesDTO(double room, double waterHeater, double heatingHysteresis, double waterHeatingHysteresis) {
        this.room = room;
        this.waterHeater = waterHeater;
        this.heatingHysteresis = heatingHysteresis;
        this.waterHeatingHysteresis = waterHeatingHysteresis;
    }

    public double getRoom() {
        return room;
    }

    public void setRoom(double room) {
        this.room = room;
    }

    public double getWaterHeater() {
        return waterHeater;
    }

    public void setWaterHeater(double waterHeater) {
        this.waterHeater = waterHeater;
    }

    public double getHeatingHysteresis() {
        return heatingHysteresis;
    }

    public void setHeatingHysteresis(double heatingHysteresis) {
        this.heatingHysteresis = heatingHysteresis;
    }

    public double getWaterHeatingHysteresis() {
        return waterHeatingHysteresis;
    }

    public void setWaterHeatingHysteresis(double waterHeatingHysteresis) {
        this.waterHeatingHysteresis = waterHeatingHysteresis;
    }
}
