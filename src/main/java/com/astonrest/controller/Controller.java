package com.astonrest.controller;

import java.io.*;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.CommandType;
import com.astonrest.command.Router;
import com.astonrest.exception.CommandException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    public Controller(ServletConfig config) {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String commandStr = request.getParameter(Attributes.COMMAND.toString().toLowerCase());
            Command command = CommandType.defineCommand(commandStr.toUpperCase());
            Router router = command.execute(request);
            if (router.getType().equals(Router.Type.FORWARD)){
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            } else {
                response.sendRedirect(router.getPage());
            }
        } catch (CommandException | IOException | IllegalArgumentException e) {
            throw new ServletException(e);
        }
    }
}