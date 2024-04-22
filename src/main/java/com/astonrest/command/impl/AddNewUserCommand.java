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
import jakarta.servlet.http.HttpSession;

public class AddNewUserCommand implements Command {
    private UserService userService;

    public AddNewUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ParameterValidator validator = ParameterValidator.getInstance();
        UserMapper mapper = UserMapperImpl.getMapper();
        String email = request.getParameter(UserArguments.LOGIN);
        String password = request.getParameter(UserArguments.PASSWORD);
        String name = request.getParameter(UserArguments.NAME);
        String surname = request.getParameter(UserArguments.SURNAME);
        int role = 1;
        UserDto userDto = new UserDto.UserDtoBuilder((long) email.length() * (long) password.length())
                .setLogin(email)
                .setPassword(password)
                .setName(name)
                .setSurname(surname)
                .setIsAdmin(role)
                .build();
        User user = mapper.map(userDto);
        if (validator.validateEmail(email) && validator.validatePassword(password)
                && validator.validateNameOrSurname(name) && validator.validateNameOrSurname(surname)) {
            try {
                if (userService.addNewUser(user)) {
                    router.setPage(Pages.USER_PAGE.getValue());
                    session.setAttribute(Attributes.USER.toString().toLowerCase(), userDto);
                } else {
                    router.setPage(Pages.INDEX_PAGE.getValue());
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            router.setPage(Pages.INDEX_PAGE.getValue());
        }
        return router;
    }
}
