package cz.ferdo.iot_server.service;

import cz.ferdo.iot_server.command.BooleanCommand;
import cz.ferdo.iot_server.command.Command;
import cz.ferdo.iot_server.command.DoubleCommand;
import cz.ferdo.iot_server.dto.*;
import cz.ferdo.iot_server.enums.CommandType;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class EspServiceImpl implements EspService {


    private final ObjectMapper objectMapper;

    public EspServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CommandResponse createResponse(DeviceMessage message) {
        List<Command> commands = switch(message.deviceType()) {
            case THERMOMETER -> processTemperature(message.payload());
            case IRRIGATION -> processIrrigation(message.payload());
        };

        return new CommandResponse(commands);
    }

    private List<Command> processTemperature(JsonNode jsonNode) {

        //TemperaturePayload payload = objectMapper.treeToValue(jsonNode, TemperaturePayload.class);
        List<Command> commands = new ArrayList<>();
        commands.add(new BooleanCommand(CommandType.LED, "OldLed", true));
        return commands;
    }

    private List<Command> processIrrigation(JsonNode jsonNode) {

        IrrigationPayload payload = objectMapper.treeToValue(jsonNode, IrrigationPayload.class);
        List<Command> commands = new ArrayList<>();
        commands.add(new BooleanCommand(CommandType.LED, "NewLed", true));
        commands.add(new DoubleCommand(CommandType.TEMP, "MeinTemp", payload.mainTemperature()));
        return commands;
    }
}
