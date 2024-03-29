package com.aston_rest_api.controller.dto;

public class AbstractDto {
    protected long id;
    public AbstractDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
