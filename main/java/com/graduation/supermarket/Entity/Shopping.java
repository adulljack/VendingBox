package com.graduation.supermarket.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//购物记录表，记录顾客的购物记录
@Entity
public class Shopping
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer shoppingId;


    public int goodsId;

    public int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(Integer shoppingId) {
        this.shoppingId = shoppingId;
    }


    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Shopping() {
    }

    public Shopping(int goodsId, int number) {
        this.goodsId = goodsId;
        this.number = number;
    }
}
