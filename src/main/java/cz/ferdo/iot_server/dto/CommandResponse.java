package cz.ferdo.iot_server.dto;

import cz.ferdo.iot_server.command.Command;

import java.util.List;

public record CommandResponse(
        List<Command> commands
) {
}
