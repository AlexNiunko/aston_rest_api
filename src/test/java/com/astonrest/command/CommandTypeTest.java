package com.astonrest.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandTypeTest {
     private String testCommand="TEST_COMMAND";
    @Test
    void defineCommand() {
       Command commandType=CommandType.defineCommand(testCommand);
        Assertions.assertEquals(CommandType.TEST_COMMAND.getCommand(),commandType);
    }
}