package com.aston_rest_api.dao;

import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface BaseDao<T> {
    boolean insert(T t) throws DaoException;
     boolean delete (T t) throws DaoException;
     List<T> findAll() throws DaoException;
     boolean update(T t) throws DaoException;

}
