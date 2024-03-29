package com.artemgggi.webshop.service;

import com.artemgggi.webshop.model.user.User;
import com.artemgggi.webshop.model.user.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
