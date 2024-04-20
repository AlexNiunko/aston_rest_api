package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.*;
import com.aston_rest_api.controller.arguments.UserArguments;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.controller.mapper.Impl.UserMapperImpl;
import com.aston_rest_api.controller.mapper.UserMapper;
import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.db.Configuration;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.UserService;
import com.aston_rest_api.service.impl.UserServiceImpl;
import com.aston_rest_api.validator.ParameterValidator;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class LoginCommand implements Command {
    private HikariDataSource config;

    public LoginCommand(HikariDataSource dataSource) {
        this.config = dataSource;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ParameterValidator validator = ParameterValidator.getInstance();
        UserMapper mapper = UserMapperImpl.getMapper();
        UserDaoImpl userDao = new UserDaoImpl(ConnectionManagerImpl.getInstance(config));
        UserService userService = new UserServiceImpl(userDao);
        String login = request.getParameter(UserArguments.LOGIN);
        String password = request.getParameter(UserArguments.PASSWORD);
        if (validator.validateEmail(login) && validator.validatePassword(password)) {
            UUID uuid = UUID.randomUUID();
            long idUser = uuid.getLeastSignificantBits();
            UserDto userDto = new UserDto.UserDtoBuilder(idUser)
                    .setLogin(login)
                    .setPassword(password)
                    .build();
            User user = mapper.map(userDto);
            try {
                UserDto userData;
                Optional<User> optionalUser = userService.authenticate(user);
                if (optionalUser.isPresent()) {
                    userData = mapper.map(optionalUser.get());
                    request.setAttribute(Attributes.USER, userData);
                    router.setRedirect();
                    router.setPage(Pages.USER_PAGE);
                } else {
                    router.setPage(Pages.INDEX_PAGE);
                }
            } catch (ServiceException e) {
                throw new CommandException("Failed login " + e);
            }
        } else {
            router.setPage(Pages.INDEX_PAGE);
        }
        return router;
    }
}
