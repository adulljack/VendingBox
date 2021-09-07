package com.graduation.supermarket.dao;

import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.Shopping;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface ShoppingDao {

    @Select("select * from shopping")
    @Results({
            @Result(id=true,property = "shoppingId",column = "shopping_id"),
            @Result(property = "goodsId",column = "goods_id"),
            @Result(property = "number",column = "number")
    })
    List<Shopping> findAll();


}
