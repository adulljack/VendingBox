package com.graduation.supermarket.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Service.GoodsService;
import com.graduation.supermarket.dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> findAll(int current,int size){
        PageHelper.startPage(current,size);
        return goodsDao.findAll();
    }

    @Override
    public void updateById(Goods goods){
        goodsDao.updateById(goods);
    }

    @Override
    public Goods findById(Integer goodsId){
        return goodsDao.findById(goodsId);
    }

    @Override
    public void addGood(Goods goods) {
       goodsDao.save(goods);
    }

    @Override
    public void deleteById(Integer id) {
        goodsDao.delete(id);
    }

    @Override
    public List<Goods> selectLike(String word, Integer current, Integer size) {
        PageHelper.startPage(current,size);
        return goodsDao.selectLike(word);
    }

    @Override
    public void updateNumById(int goodsId,int num) {
        goodsDao.updateNum(goodsId,num);
    }
}
