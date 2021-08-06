package com.graduation.supermarket.dao;

import com.graduation.supermarket.Entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsDao {

    @Select("select * from goods")
    @Results({
            @Result(id=true,property = "goodsId",column = "goods_id"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "imgUrl",column = "img_url"),
            @Result(property = "price",column = "price"),
            @Result(property = "number",column = "number")
    })
    List<Goods> findAll();

    @Update("update goods set goods_name=#{goodsName},img_url=#{imgUrl},price=#{price},number=#{number} where goods_id=#{goodsId}")
    @Results({
            @Result(id=true,property = "goodsId",column = "goods_id"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "imgUrl",column = "img_url"),
            @Result(property = "price",column = "price"),
            @Result(property = "number",column = "number")
    })
    void updateById(Goods goods);

    @Select("select * from goods where goods_id=#{goodsId}")
    @Results({
            @Result(id=true,property = "goodsId",column = "goods_id"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "imgUrl",column = "img_url"),
            @Result(property = "price",column = "price"),
            @Result(property = "number",column = "number")
    })
    Goods findById(Integer goodsId);

    @Insert("insert into goods(goods_name,img_url,price,number) values(#{goodsName},#{imgUrl},#{price},#{number})")
    @Results({
            @Result(id=true,property = "goodsId",column = "goods_id"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "imgUrl",column = "img_url"),
            @Result(property = "price",column = "price"),
            @Result(property = "number",column = "number")
    })
    void save(Goods goods);

    @Delete("delete from goods where goods_id=#{id}")
    void delete(Integer id);

    @Select("select * from goods where goods_name like concat('%',#{word},'%')")
    @Results({
            @Result(id=true,property = "goodsId",column = "goods_id"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "imgUrl",column = "img_url"),
            @Result(property = "price",column = "price"),
            @Result(property = "number",column = "number")
    })
    List<Goods> selectLike(String word);

    @Update("update goods set number=number-#{num} where goods_id=#{goodsId}")
    void updateNum(@Param("goodsId") int goodsId,@Param("num") int num);
}
