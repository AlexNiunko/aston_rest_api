package com.astonrest.dao;

import com.astonrest.exception.DaoException;

import java.util.List;

public interface BaseDao<T> {
    boolean insert(T t) throws DaoException;
     boolean delete (T t) throws DaoException;
     List<T> findAll() throws DaoException;
     boolean update(T t) throws DaoException;

}
