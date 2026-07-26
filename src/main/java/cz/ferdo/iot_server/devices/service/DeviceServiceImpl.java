package cz.ferdo.iot_server.devices.service;

import cz.ferdo.iot_server.advice.DeviceAlreadyExistsException;
import cz.ferdo.iot_server.advice.DeviceNotFoundException;
import cz.ferdo.iot_server.commands.command.BooleanCommand;
import cz.ferdo.iot_server.commands.command.Command;
import cz.ferdo.iot_server.commands.command.DoubleCommand;
import cz.ferdo.iot_server.commands.command.dto.CommandResponse;
import cz.ferdo.iot_server.commands.command.dto.IrrigationPayload;
import cz.ferdo.iot_server.devices.dto.DeviceDTO;
import cz.ferdo.iot_server.devices.dto.DeviceMessageDTO;
import cz.ferdo.iot_server.devices.entity.DeviceEntity;
import cz.ferdo.iot_server.devices.mapper.DeviceMapper;
import cz.ferdo.iot_server.devices.repository.DeviceRepository;
import cz.ferdo.iot_server.enums.CommandType;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private boolean led = false;
    private final ObjectMapper objectMapper;

    private final DeviceMapper deviceMapper;
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(ObjectMapper objectMapper, DeviceMapper deviceMapper, DeviceRepository deviceRepository)
    {
        this.objectMapper = objectMapper;
        this.deviceMapper = deviceMapper;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceDTO add(DeviceDTO deviceDTO) {

            if (deviceRepository.existsByDeviceName(deviceDTO.getName())) {
                throw new DeviceAlreadyExistsException(deviceDTO.getName());
            }

            DeviceEntity deviceEntity = deviceMapper.toEntity(deviceDTO);

            DeviceEntity saved = deviceRepository.save(deviceEntity);

            return deviceMapper.toDTO(saved);
    }

    @Override
    public DeviceDTO update(Long deviceId, DeviceDTO deviceDTO) {
        return new DeviceDTO(1L,"device","device","0",null);
    }

    @Override
    public DeviceDTO delete(Long deviceId) {
        return new DeviceDTO(1L,"device","device","0",null);
    }

    @Override
    public List<DeviceDTO> findAll() {
        List<DeviceEntity> deviceEntities = deviceRepository.findAll();

        return deviceEntities.stream()
                .map(deviceMapper::toDTO)
                .toList();
    }


    @Override
    public DeviceDTO findByName(String deviceName) {
        DeviceEntity entity = deviceRepository.findByDeviceName(deviceName)
                .orElseThrow(() -> new DeviceNotFoundException(deviceName));

        return deviceMapper.toDTO(entity);
    }

    @Override
    public CommandResponse createResponse(DeviceMessageDTO message) {
        List<Command> commands = switch(message.deviceType()) {
            case WEATHER -> processController(message.payload());
            case THERMOMETER -> processTemperature(message.payload());
            case IRRIGATION -> processIrrigation(message.payload());
            case CONTROLLER -> processController(message.payload());
        };

        return new CommandResponse(commands);
    }

    // === PRIVATE ===

    private List<Command> processWeather(JsonNode jsonNode) {

        //TemperaturePayload payload = objectMapper.treeToValue(jsonNode, TemperaturePayload.class);
        led = !led;
        List<Command> commands = new ArrayList<>();
        commands.add(new BooleanCommand(CommandType.LED, "OldLed", led));
        return commands;
    }

    private List<Command> processTemperature(JsonNode jsonNode) {

        //TemperaturePayload payload = objectMapper.treeToValue(jsonNode, TemperaturePayload.class);
        led = !led;
        List<Command> commands = new ArrayList<>();
        commands.add(new BooleanCommand(CommandType.LED, "OldLed", led));
        return commands;
    }

    private List<Command> processIrrigation(JsonNode jsonNode) {

        IrrigationPayload payload = objectMapper.treeToValue(jsonNode, IrrigationPayload.class);
        led = !led;
        List<Command> commands = new ArrayList<>();
        commands.add(new BooleanCommand(CommandType.LED, "NewLed", led));
        commands.add(new DoubleCommand(CommandType.TEMP, "MeinTemp", payload.mainTemperature()));
        return commands;
    }

    private List<Command> processController(JsonNode jsonNode) {

        IrrigationPayload payload = objectMapper.treeToValue(jsonNode, IrrigationPayload.class);
        led = !led;
        List<Command> commands = new ArrayList<>();
        commands.add(new BooleanCommand(CommandType.LED, "NewLed", led));
        commands.add(new DoubleCommand(CommandType.TEMP, "MeinTemp", payload.mainTemperature()));
        return commands;
    }
}
