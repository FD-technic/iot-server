package cz.ferdo.iot_server.command;

import cz.ferdo.iot_server.enums.CommandType;

public record IntegerCommand(CommandType type, String name, int value) implements Command {
}
