package com.aston_rest_api.dao;

import com.aston_rest_api.model.AbstractEntity;

import java.util.List;

public abstract class BaseDao<T extends AbstractEntity> {
    public abstract boolean insert(T t);
    public abstract boolean delete (T t);
    public abstract List<T> findAll();
    public abstract boolean update(T t);
}
