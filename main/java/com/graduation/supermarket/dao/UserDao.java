package com.graduation.supermarket.dao;

import com.graduation.supermarket.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("select * from user where uid=#{username} and password=#{password}")
    User findByIDAndPassword(@Param("username") String username, @Param("password") String password);
}
