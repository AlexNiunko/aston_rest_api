package com.astonrest.command;

import com.astonrest.command.impl.*;
import com.astonrest.dao.impl.ProductDaoImpl;
import com.astonrest.dao.impl.SaleDaoImpl;
import com.astonrest.dao.impl.UserDaoImpl;
import com.astonrest.db.Configuration;
import com.astonrest.db.ConnectionManagerImpl;
import com.astonrest.service.impl.ProductServiceImpl;
import com.astonrest.service.impl.SaleServiceImpl;
import com.astonrest.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    ADD_NEW_USER_COMMAND(new AddNewUserCommand(UserServiceImpl.getUserService(UserDaoImpl.getUserDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    ADD_NEW_PRODUCT_COMMAND(new AddNewProductCommand
            (ProductServiceImpl.getProductService(ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    BUY_PRODUCT_COMMAND(new BuyProductCommand(SaleServiceImpl.getSaleService(
            ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource)),
            SaleDaoImpl.getSaleDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))),
            ProductServiceImpl.getProductService(ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    DELETE_PRODUCT_COMMAND(new DeleteProductCommand(ProductServiceImpl.getProductService(ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    LOGIN_COMMAND(new LoginCommand(UserServiceImpl.getUserService(UserDaoImpl.getUserDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    SHOW_ALL_PRODUCTS_COMMAND(new ShowAllProductsCommand(ProductServiceImpl.getProductService(ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    SHOW_ALL_SALES_COMMAND(new ShowAllSalesCommand(SaleServiceImpl.getSaleService(
            ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource)),
            SaleDaoImpl.getSaleDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    SHOW_PRODCUT_SALES_COMMAND(new ShowProductSalesCommand(SaleServiceImpl.getSaleService(
            ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource)),
            SaleDaoImpl.getSaleDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))),
            ProductServiceImpl.getProductService(ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    SHOW_SALES_BY_DATE_COMMAND(new ShowSalesByDateCommand(SaleServiceImpl.getSaleService(
            ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource)),
            SaleDaoImpl.getSaleDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    SHOW_USER_PURCHASES_COMMAND(new ShowUserPurchases(UserServiceImpl.getUserService(UserDaoImpl.getUserDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    UPDATE_PRODUCT_COMMAND(new UpdateProductCommand(ProductServiceImpl.getProductService(ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    UPDATE_PROFILE_COMMAND(new UpdateProfileCommand(UserServiceImpl.getUserService(UserDaoImpl.getUserDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    UPDATE_SALE_COMMAND(new UpdateSaleCommand(SaleServiceImpl.getSaleService(
            ProductDaoImpl.getProductDao(ConnectionManagerImpl.getInstance(Configuration.dataSource)),
            SaleDaoImpl.getSaleDao(ConnectionManagerImpl.getInstance(Configuration.dataSource))))),
    TEST_COMMAND(new TestCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandStr) {
        CommandType currentCommand;
        Optional<CommandType> ifExist = Arrays.stream(CommandType.values())
                .filter(s -> s == CommandType.valueOf(commandStr))
                .findAny();
        currentCommand = (ifExist.orElse(CommandType.DEFAULT));
        return currentCommand.command;
    }
}
