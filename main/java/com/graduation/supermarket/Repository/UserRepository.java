package com.graduation.supermarket.Repository;

import com.graduation.supermarket.Entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Integer>{

    @Query(value = "select * from user where uid=:userID and password=:password",nativeQuery = true)
    User findByIDAndPassword(@Param("userID") String userID, @Param("password") String password);
}
