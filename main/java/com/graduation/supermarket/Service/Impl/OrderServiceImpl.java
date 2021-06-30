package com.graduation.supermarket.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.graduation.supermarket.Entity.OrderGoods;
import com.graduation.supermarket.Entity.OrderViewObject;
import com.graduation.supermarket.Entity.Orders;
import com.graduation.supermarket.Service.OrderService;
import com.graduation.supermarket.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void save(Orders order) {
        orderDao.save(order);
    }

    @Override
    public Orders findById(String orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public void saveInOrderGoods(OrderGoods orderGoods) {
        orderDao.saveIntoOrderGoods(orderGoods);
    }

    @Override
    public void updateById(Orders order) {
        orderDao.updateById(order);
    }

    @Override
    public List<OrderViewObject> findAll(Integer current, Integer size) {
        PageHelper.startPage(current,size);
        return orderDao.findAll(current,size);
    }
}
