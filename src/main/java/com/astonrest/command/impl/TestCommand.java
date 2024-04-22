package com.astonrest.command.impl;

import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class TestCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPage(Pages.INDEX_PAGE.getValue());
        router.setRedirect();
        return router;
    }
}
