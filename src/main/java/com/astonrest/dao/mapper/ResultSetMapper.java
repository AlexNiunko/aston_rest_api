package com.astonrest.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ResultSetMapper<T> {
    Optional<T> mapItem(ResultSet resultSet) throws SQLException;

    List<T> mapListItems(ResultSet resultSet) throws SQLException;
}
