package com.graduation.supermarket.Service;

import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.OrderGoods;
import com.graduation.supermarket.Entity.OrderViewObject;
import com.graduation.supermarket.Entity.Orders;

import java.util.List;

public interface OrderService {
    void save(Orders order);

    Orders findById(String orderId);

    void saveInOrderGoods(OrderGoods orderGoods);

    void updateById(Orders order);

    List<OrderViewObject> findAll(Integer current, Integer size);
}
