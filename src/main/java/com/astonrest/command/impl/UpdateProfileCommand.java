package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
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

public class UpdateProfileCommand implements Command {
    private UserService userService;

    public UpdateProfileCommand(UserService userService) {
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
        UserDto userDtoProfile = (UserDto) session.getAttribute(Attributes.USER.toString().toLowerCase());
        if (validator.validateEmail(email) && validator.validatePassword(password)
                && validator.validateNameOrSurname(name) && validator.validateNameOrSurname(surname)) {
            UserDto userDto = new UserDto.UserDtoBuilder(userDtoProfile.getIdUser())
                    .setLogin(email)
                    .setPassword(password)
                    .setName(name)
                    .setSurname(surname)
                    .setIsAdmin(role)
                    .build();
            User user = mapper.map(userDto);
            try {
                if (userService.updateUser(user)) {
                    router.setPage(Pages.USER_PAGE.getValue());
                    session.setAttribute(Attributes.USER.toString().toLowerCase(), userDto);
                } else {
                    router.setPage(Pages.UPDATE_PROFILE_PAGE.getValue());
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            router.setPage(Pages.UPDATE_PROFILE_PAGE.getValue());
        }
        return router;
    }
}
