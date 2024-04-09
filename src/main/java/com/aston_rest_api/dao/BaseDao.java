package com.aston_rest_api.dao;

import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.AbstractEntity;
import com.aston_rest_api.model.Sale;

import java.time.LocalDate;
import java.util.List;

public abstract class BaseDao<T extends AbstractEntity> {
    public abstract boolean insert(T t) throws DaoException;
    public abstract boolean delete (T t) throws DaoException;
    public abstract List<T> findAll() throws DaoException;
    public abstract boolean update(T t) throws DaoException;

}
