package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.controller.mapper.Impl.ProductMapperImpl;
import com.aston_rest_api.controller.mapper.Impl.UserMapperImpl;
import com.aston_rest_api.controller.mapper.ProductMapper;
import com.aston_rest_api.controller.mapper.UserMapper;
import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.db.Configuration;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.UserService;
import com.aston_rest_api.service.impl.UserServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowUserPurchases implements Command {
    private HikariDataSource config;

    public ShowUserPurchases(HikariDataSource config) {
        this.config=config;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        HttpSession session= request.getSession();
        UserDaoImpl userDao=new UserDaoImpl(ConnectionManagerImpl.getInstance(config));
        UserService userService=new UserServiceImpl(userDao);
        UserMapper userMapper= UserMapperImpl.getMapper();
        ProductMapper productMapper= ProductMapperImpl.getMapper();
        UserDto userDto = (UserDto) session.getAttribute(Attributes.USER);
        User user= userMapper.map(userDto);
        HashMap<Long, Product>purchases =new HashMap<>();
        try{
            purchases=userService.selectUserPurchases(user);
            List<ProductDto>purchasesDto=new ArrayList<>();
            for (Product item: purchases.values()) {
                purchasesDto.add(productMapper.map(item));
            }
            session.setAttribute(Attributes.USER_PURCHASES,purchasesDto);
            router.setPage(Pages.PURCHASES_PAGE);
        }catch (ServiceException e){
            throw new CommandException("Failed find user purchases "+e);
        }
        return router;
    }
}
