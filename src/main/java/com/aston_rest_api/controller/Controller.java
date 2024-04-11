package com.aston_rest_api.controller;

import java.io.*;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.CommandType;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.db.Configuration;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    public Controller() {
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        try {
            String commandStr = request.getParameter(Attributes.COMMAND);
            Command command = CommandType.defineCommand(commandStr.toUpperCase());
            Router router = command.execute(request);
            switch (router.getType()) {
                case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
                case REDIRECT -> response.sendRedirect(router.getPage());
            }
        } catch (CommandException | IOException |IllegalArgumentException e) {
            throw new ServletException(e);
        }
    }
}