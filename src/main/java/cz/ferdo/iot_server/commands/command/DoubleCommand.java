package cz.ferdo.iot_server.commands.command;

import cz.ferdo.iot_server.enums.CommandType;

public record DoubleCommand(CommandType type, String name, double value) implements Command {
}
