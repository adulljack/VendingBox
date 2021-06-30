package com.graduation.supermarket.Service;

import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.User;

import java.util.List;

public interface GoodsService {

    List<Goods> findAll(int current,int size);

    void updateById(Goods goods);

    Goods findById(Integer goodsId);

    void addGood(Goods goods);

    void deleteById(Integer id);

    List<Goods> selectLike(String word, Integer current, Integer size);

    void updateNumById(int goodsId,int num);
}
