package com.graduation.supermarket.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.Shopping;
import com.graduation.supermarket.Service.GoodsService;
import com.graduation.supermarket.Service.ShoppingService;
import com.graduation.supermarket.dao.GoodsDao;
import com.graduation.supermarket.dao.ShoppingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingDao shoppingDao;

    @Override
    public List<Shopping> findAll(int current, int size){
        PageHelper.startPage(current,size);
        return shoppingDao.findAll();
    }
}
