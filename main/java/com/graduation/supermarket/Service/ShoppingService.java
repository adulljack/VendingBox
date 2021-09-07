package com.graduation.supermarket.Service;

import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.Shopping;

import java.util.List;

public interface ShoppingService {

    List<Shopping> findAll(int current, int size);


}
