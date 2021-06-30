package com.graduation.supermarket.Repository;

import com.graduation.supermarket.Entity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingRepository extends JpaRepository<Shopping,Integer>
{
    @Query(value = "select * from shopping",nativeQuery = true)
    public List<Shopping> findAll();

    @Modifying
    @Query(value = "delete from shopping",nativeQuery = true)
    public void deleteAll();
}
