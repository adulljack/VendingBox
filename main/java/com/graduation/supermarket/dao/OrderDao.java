package com.graduation.supermarket.dao;

import com.graduation.supermarket.Entity.OrderGoods;
import com.graduation.supermarket.Entity.OrderViewObject;
import com.graduation.supermarket.Entity.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderDao {

    @Insert(value = "insert into orders(id,total_fee,status) values(#{id},#{totalFee},#{status})")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "totalFee",column = "total_fee"),
            @Result(property = "status",column = "status")
    })
    void save(Orders orders);

    @Select("select * from orders where id=#{orderId}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "totalFee",column = "total_fee"),
            @Result(property = "status",column = "status")
    })
    Orders findById(String orderId);

    @Insert("insert into order_goods(order_id,goods_id) values(#{orderId},#{goodsId})")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "order_id",column = "orderId"),
            @Result(property = "goods_id",column = "goodsId")
    })
    void saveIntoOrderGoods(OrderGoods orderGoods);

    @Update("update orders set status=#{status} where id=#{id}")
    void updateById(Orders order);

    @Select("select odr.id,odr.total_fee,odr.status,odr.create_time,gd.goods_name,gd.img_url from orders odr LEFT JOIN order_goods og on odr.id = og.order_id LEFT JOIN goods gd ON gd.goods_id=og.goods_id")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "totalFee",column = "total_fee"),
            @Result(property = "status",column = "status"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "imgUrl",column = "img_url")
    })
    List<OrderViewObject> findAll(Integer current, Integer size);
}
