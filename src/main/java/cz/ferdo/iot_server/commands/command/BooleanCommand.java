package cz.ferdo.iot_server.commands.command;

import cz.ferdo.iot_server.enums.CommandType;

public record BooleanCommand(CommandType type, String name, boolean enabled) implements Command {
}
