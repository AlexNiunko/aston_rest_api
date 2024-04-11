package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.SaleArguments;
import com.aston_rest_api.controller.dto.SaleDto;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.controller.mapper.Impl.SaleMapperImpl;
import com.aston_rest_api.controller.mapper.Impl.UserMapperImpl;
import com.aston_rest_api.controller.mapper.SaleMapper;
import com.aston_rest_api.controller.mapper.UserMapper;
import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.SaleDaoImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.ProductService;
import com.aston_rest_api.service.SaleService;
import com.aston_rest_api.service.impl.ProductServiceImpl;
import com.aston_rest_api.service.impl.SaleServiceImpl;
import com.aston_rest_api.validator.ParameterValidator;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShowAllSalesCommand implements Command {
    private HikariDataSource config;
    public ShowAllSalesCommand(HikariDataSource config) {
        this.config=config;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        HttpSession session= request.getSession();
        SaleMapper saleMapper= SaleMapperImpl.getMapper();
        ProductDaoImpl productDao=new ProductDaoImpl(ConnectionManagerImpl.getInstance(config));
        SaleDaoImpl saleDao=new SaleDaoImpl(ConnectionManagerImpl.getInstance(config));
        UserMapper userMapper= UserMapperImpl.getMapper();
        SaleService saleService=new SaleServiceImpl(productDao,saleDao);
        try{
            List<Sale>saleList=saleService.findAllSales();
            List<SaleDto>saleDtos=new ArrayList<>();
            for (Sale sale:saleList) {
                saleDtos.add(saleMapper.map(sale));
            }
            session.setAttribute(Attributes.SALES,saleDtos);
            router.setPage(Pages.SALES_PAGE);
        }catch (ServiceException e){
            throw new CommandException("Failed buy product "+e);
        }
        return router;
    }
}
