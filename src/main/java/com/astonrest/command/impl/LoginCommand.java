package com.astonrest.command.impl;

import com.astonrest.command.*;
import com.astonrest.controller.arguments.UserArguments;
import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.impl.UserMapperImpl;
import com.astonrest.controller.mapper.UserMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.User;
import com.astonrest.service.UserService;
import com.astonrest.validator.ParameterValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

public class LoginCommand implements Command {
    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ParameterValidator validator = ParameterValidator.getInstance();
        UserMapper mapper = UserMapperImpl.getMapper();
        String login = request.getParameter(UserArguments.LOGIN);
        String password = request.getParameter(UserArguments.PASSWORD);
        if (validator.validateEmail(login) && validator.validatePassword(password)) {
            UserDto userDto = new UserDto.UserDtoBuilder((long) login.length() * (long) password.length())
                    .setName("")
                    .setSurname("")
                    .setLogin(login)
                    .setPassword(password)
                    .build();
            User user = mapper.map(userDto);
            try {
                UserDto userData;
                Optional<User> optionalUser = userService.authenticate(user);
                if (optionalUser.isPresent()) {
                    userData = mapper.map(optionalUser.get());
                    request.setAttribute(Attributes.USER.toString().toLowerCase(), userData);
                    router.setRedirect();
                    router.setPage(Pages.USER_PAGE.getValue());
                } else {
                    router.setPage(Pages.INDEX_PAGE.getValue());
                }
            } catch (ServiceException e) {
                throw new CommandException("Failed login " + e);
            }
        } else {
            router.setPage(Pages.INDEX_PAGE.getValue());
        }
        return router;
    }
}
