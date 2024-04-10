package com.aston_rest_api.controller.mapper;

import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.model.User;

public interface UserMapper {
    User map(UserDto userDto);
    UserDto map(User user);
}
