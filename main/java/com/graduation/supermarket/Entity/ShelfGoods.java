package com.graduation.supermarket.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//货架商品表，记录货架上的商品种类与数量
@Entity
public class ShelfGoods
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer shelfGoodsId;

    public int goodsId;
    public int number;

    public Integer getShelfGoodsId() {
        return shelfGoodsId;
    }

    public void setShelfGoodsId(Integer shelfGoodsId) {
        this.shelfGoodsId = shelfGoodsId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ShelfGoods(int goodsId, int number) {
        this.goodsId = goodsId;
        this.number = number;
    }

    public ShelfGoods() {
    }
}
