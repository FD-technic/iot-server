package cz.ferdo.iot_server.command;

import cz.ferdo.iot_server.enums.CommandType;

public interface Command {
    CommandType type();
    String name();
}
