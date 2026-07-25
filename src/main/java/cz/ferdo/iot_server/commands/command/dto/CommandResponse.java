package cz.ferdo.iot_server.commands.command.dto;

import cz.ferdo.iot_server.commands.command.Command;

import java.util.List;

public record CommandResponse(
        List<Command> commands
) {
}
