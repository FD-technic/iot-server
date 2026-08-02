package cz.ferdo.iot_server.commands.command;

import cz.ferdo.iot_server.core.enums.CommandType;

public record IntegerCommand(CommandType type, String name, int value) implements Command {
}
