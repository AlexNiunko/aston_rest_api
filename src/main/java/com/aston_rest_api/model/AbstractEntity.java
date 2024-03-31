package com.aston_rest_api.model;

public class AbstractEntity {
    protected long id;
    public AbstractEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
