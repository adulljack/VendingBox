package com.graduation.supermarket.Repository;

import com.graduation.supermarket.Entity.ShelfGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShelfGoodsRepository extends JpaRepository<ShelfGoods,Integer>
{

    @Query(value = "select * from shelf_goods",nativeQuery = true)
    public List<ShelfGoods> getAll();

    @Modifying
    @Query(value = "delete from shelf_goods",nativeQuery = true)
    public void deleteAll();

}
