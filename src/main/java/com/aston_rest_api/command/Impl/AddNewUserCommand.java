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
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import javax.xml.validation.Validator;
import java.util.UUID;

public class AddNewUserCommand  implements Command {
    private HikariDataSource config;
    public AddNewUserCommand(HikariDataSource config) {
        this.config=config;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        UserDaoImpl userDao=new UserDaoImpl(ConnectionManagerImpl.getInstance(config));
        UserService userService=new UserServiceImpl(userDao);
        HttpSession session= request.getSession();
        ParameterValidator validator=ParameterValidator.getInstance();
        UserMapper mapper= UserMapperImpl.getMapper();
        UUID uuid=UUID.randomUUID();
        long idDtoUser=uuid.getMostSignificantBits();
        String email=request.getParameter(UserArguments.LOGIN);
        String password=request.getParameter(UserArguments.PASSWORD);
        String name=request.getParameter(UserArguments.NAME);
        String surname=request.getParameter(UserArguments.SURNAME);
        int role=1;
        if ( validator.validateEmail(email) && validator.validatePassword(password)
                && validator.validateNameOrSurname(name) && validator.validateNameOrSurname(surname) ){
            UserDto userDto=new UserDto.UserDtoBuilder(idDtoUser)
                    .setLogin(email)
                    .setPassword(password)
                    .setName(name)
                    .setSurname(surname)
                    .setIsAdmin(role)
                    .build();
            User user =mapper.map(userDto);
            try {
                if (userService.addNewUser(user)){
                    router.setPage(Pages.USER_PAGE);
                    session.setAttribute(Attributes.USER,userDto);
                } else {
                    router.setPage(Pages.INDEX_PAGE);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }else {
            router.setPage(Pages.INDEX_PAGE);
        }
        return router;
    }
}
