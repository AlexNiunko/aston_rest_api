package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.impl.ProductMapperImpl;
import com.astonrest.controller.mapper.impl.UserMapperImpl;
import com.astonrest.controller.mapper.ProductMapper;
import com.astonrest.controller.mapper.UserMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;
import com.astonrest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class ShowUserPurchases implements Command {
    private UserService userService;

    public ShowUserPurchases(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        UserMapper userMapper = UserMapperImpl.getMapper();
        ProductMapper productMapper = ProductMapperImpl.getMapper();
        UserDto userDto = (UserDto) session.getAttribute(Attributes.USER.toString().toLowerCase());
        User user = userMapper.map(userDto);
        List<Product> purchases = new ArrayList<>();
        try {
            purchases = userService.selectUserPurchases(user);
            List<ProductDto> purchasesDto = new ArrayList<>();
            for (Product item : purchases) {
                purchasesDto.add(productMapper.map(item));
            }
            session.setAttribute(Attributes.USER_PURCHASES.toString().toLowerCase(), purchasesDto);
            router.setPage(Pages.PURCHASES_PAGE.getValue());
        } catch (ServiceException e) {
            throw new CommandException("Failed find user purchases " + e);
        }
        return router;
    }
}
