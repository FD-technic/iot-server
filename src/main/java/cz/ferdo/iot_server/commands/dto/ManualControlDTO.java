package cz.ferdo.iot_server.commands.dto;

import cz.ferdo.iot_server.devices.enums.ValveDirection;

public class ManualControlDTO {
    private boolean waterHeaterPump = false;
    private boolean heatingPump = false;
    private ValveDirection valveDirection = ValveDirection.STOP;

    public ManualControlDTO() {}

    public ManualControlDTO(boolean waterHeaterPump, boolean heatingPump, ValveDirection valveDirection) {
        this.waterHeaterPump = waterHeaterPump;
        this.heatingPump = heatingPump;
        this.valveDirection = valveDirection;
    }

    public boolean isWaterHeaterPump() {
        return waterHeaterPump;
    }

    public void setWaterHeaterPump(boolean waterHeaterPump) {
        this.waterHeaterPump = waterHeaterPump;
    }

    public boolean isHeatingPump() {
        return heatingPump;
    }

    public void setHeatingPump(boolean heatingPump) {
        this.heatingPump = heatingPump;
    }

    public ValveDirection getValveDirection() {
        return valveDirection;
    }

    public void setValveDirection(ValveDirection valveDirection) {
        this.valveDirection = valveDirection;
    }
}
