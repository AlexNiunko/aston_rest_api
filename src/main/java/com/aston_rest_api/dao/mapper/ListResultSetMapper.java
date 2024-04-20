package com.aston_rest_api.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ListResultSetMapper<U> {
    List<U> mapItemEntities(ResultSet resultSet) throws SQLException;

}
