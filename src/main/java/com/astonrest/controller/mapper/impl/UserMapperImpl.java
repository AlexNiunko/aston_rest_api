package com.astonrest.controller.mapper.impl;

import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.UserMapper;
import com.astonrest.model.User;

public class UserMapperImpl implements UserMapper {
    private static UserMapperImpl mapper = new UserMapperImpl();

    private UserMapperImpl() {
    }

    public static UserMapperImpl getMapper() {
        return mapper;
    }

    @Override
    public User map(UserDto userDto) {
        return new User
                .UserBuilder(userDto.getIdUser())
                .setLogin(userDto.getLogin())
                .setPassword(userDto.getPassword())
                .setName(userDto.getName())
                .setSurname(userDto.getSurname())
                .setUsersRole(userDto.getIsAdmin())
                .build();
    }

    @Override
    public UserDto map(User user) {
        return new UserDto
                .UserDtoBuilder(user.getId())
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setIsAdmin(user.getUsersRole())
                .build();
    }
}
