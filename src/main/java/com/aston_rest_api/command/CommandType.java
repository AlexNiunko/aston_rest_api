package com.aston_rest_api.command;

import com.aston_rest_api.command.Impl.DefaultCommand;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    DEFAULT(new DefaultCommand());




    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    private Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandStr) {

        CommandType currentCommand;
        Optional<CommandType> ifExist = Arrays.stream(CommandType.values())
                .filter(s -> s == CommandType.valueOf(commandStr))
                .findAny();
        currentCommand = (ifExist.orElse(CommandType.DEFAULT));
        return currentCommand.command;
    }
}
