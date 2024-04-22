package com.astonrest.controller.mapper.impl;

import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.UserMapper;
import com.astonrest.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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