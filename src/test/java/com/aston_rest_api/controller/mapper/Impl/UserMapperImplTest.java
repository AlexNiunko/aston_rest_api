package com.aston_rest_api.controller.mapper.Impl;

import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.controller.mapper.SaleMapper;
import com.aston_rest_api.controller.mapper.UserMapper;
import com.aston_rest_api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {
    private UserMapper mapper=UserMapperImpl.getMapper();

    private User user = new User.UserBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .build();
    private UserDto userDto=new UserDto.UserDtoBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .build();

    @Test
    void shouldMapToModel() {
        User actual=mapper.map(userDto);
        Assertions.assertEquals(user,actual);
    }

    @Test
    void shouldMapToDto() {
        UserDto actual=mapper.map(user);
        Assertions.assertEquals(userDto,actual);
    }
}