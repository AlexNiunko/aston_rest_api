package com.aston_rest_api.dao.mapper;

import com.aston_rest_api.model.AbstractEntity;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ResultSetMapper <T,U extends AbstractEntity>{
    Optional<T> mapItem(ResultSet resultSet)throws SQLException;
    List<T> mapListItems(ResultSet resultSet)throws SQLException;
    Map<Long, U> mapItemEntities(ResultSet resultSet)throws SQLException;
}
