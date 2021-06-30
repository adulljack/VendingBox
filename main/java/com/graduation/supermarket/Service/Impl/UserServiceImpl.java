package com.graduation.supermarket.Service.Impl;

import com.graduation.supermarket.Entity.User;
import com.graduation.supermarket.Repository.UserRepository;
import com.graduation.supermarket.Service.UserService;
import com.graduation.supermarket.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByNameAndPassword(String username, String password) {
        return userDao.findByIDAndPassword(username,password);
    }
}
