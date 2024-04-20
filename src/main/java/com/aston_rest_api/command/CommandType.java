package com.aston_rest_api.command;

import com.aston_rest_api.command.Impl.*;
import com.aston_rest_api.db.Configuration;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    ADD_NEW_USER_COMMAND(new AddNewUserCommand(Configuration.dataSource)),
    ADD_NEW_PRODUCT_COMMAND(new AddNewProductCommand(Configuration.dataSource)),
    BUY_PRODUCT_COMMAND(new BuyProductCommand(Configuration.dataSource)),
    DELETE_PRODUCT_COMMAND(new DeleteProductCommand(Configuration.dataSource)),
    LOGIN_COMMAND(new LoginCommand(Configuration.dataSource)),
    SHOW_ALL_PRODUCTS_COMMAND(new ShowAllProductsCommand(Configuration.dataSource)),
    SHOW_ALL_SALES_COMMAND(new ShowAllSalesCommand(Configuration.dataSource)),
    SHOW_PRODCUT_SALES_COMMAND(new ShowProductSalesCommand(Configuration.dataSource)),
    SHOW_SALES_BY_DATE_COMMAND(new ShowSalesByDateCommand(Configuration.dataSource)),
    SHOW_USER_PURCHASES_COMMAND(new ShowUserPurchases(Configuration.dataSource)),
    UPDATE_PRODUCT_COMMAND(new UpdateProductCommand(Configuration.dataSource)),
    UPDATE_PROFILE_COMMAND(new UpdateProfileCommand(Configuration.dataSource)),
    UPDATE_SALE_COMMAND(new UpdateSaleCommand(Configuration.dataSource)),
    TEST_COMMAND(new TestCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    private Command getCommand(HikariDataSource dataSource) {
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
