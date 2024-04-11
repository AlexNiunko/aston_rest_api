package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCommandTest {
    private static Command command;
    private static HttpServletRequest request;

    @BeforeAll
    static void init() {
        command = new DefaultCommand();
        request=Mockito.mock(HttpServletRequest.class);
    }


    @Test
    void execute() throws CommandException {
        Assertions.assertEquals(Pages.INDEX_PAGE,command.execute(request).getPage() );
    }
}