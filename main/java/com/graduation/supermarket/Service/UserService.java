package com.graduation.supermarket.Service;

import com.graduation.supermarket.Entity.User;

public interface UserService {

    User findUserByNameAndPassword(String username, String password);
}
