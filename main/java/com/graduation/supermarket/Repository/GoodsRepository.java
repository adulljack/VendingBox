package com.graduation.supermarket.Repository;

import com.graduation.supermarket.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods,Integer>
{
    @Query(value = "select * from goods",nativeQuery = true)
    public List<Goods> getAllGoods();

    public Goods getGoodsByGoodsId(int id);

}
