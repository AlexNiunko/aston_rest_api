package com.astonrest.controller.mapper;

import com.astonrest.controller.dto.UserDto;
import com.astonrest.model.User;

public interface UserMapper {
    User map(UserDto userDto);

    UserDto map(User user);
}
