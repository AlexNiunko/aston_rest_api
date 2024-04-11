package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class TestCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        router.setPage(Pages.INDEX_PAGE);
        router.setRedirect();
        return router;
    }


}