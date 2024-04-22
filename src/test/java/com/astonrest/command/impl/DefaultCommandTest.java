package com.astonrest.command.impl;

import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultCommandTest {
    private static Command command;
    private static HttpServletRequest request;

    @BeforeAll
    static void init() {
        command = new DefaultCommand();
        request = Mockito.mock(HttpServletRequest.class);
    }


    @Test
    void execute() throws CommandException {
        Assertions.assertEquals(Pages.INDEX_PAGE.getValue(), command.execute(request).getPage());
    }
}