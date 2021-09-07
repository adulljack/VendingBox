package com.graduation.supermarket.Controller;

import com.graduation.supermarket.Analyse.ObjectAnalyse;
import com.graduation.supermarket.AssitClass.CameraThread;
import com.graduation.supermarket.AssitClass.ExShopping;
import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.ShelfGoods;
import com.graduation.supermarket.Entity.Shopping;
import com.graduation.supermarket.Repository.GoodsRepository;
import com.graduation.supermarket.Repository.ShelfGoodsRepository;
import com.graduation.supermarket.Repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

@CrossOrigin
@RestController
public class HardwareController {

    @Autowired
    ShelfGoodsRepository shelfGoodsRepository;
    @Autowired
    ShoppingRepository shoppingRepository;
    @Autowired
    GoodsRepository goodsRepository;

    long openTime;
    long closeTime;
    int  flag =0;

    @GetMapping(value = "/hardware_position_switch")
    @Transactional
    public void hardwareControl(@RequestParam("tag") String tag) {
        if (tag.equals("open")) {
            System.out.println("open");
            this.openTime=System.currentTimeMillis();
            System.out.println(openTime);
            shoppingRepository.deleteAll();
            shelfGoodsRepository.deleteAll();
            imwrite("py/open.jpg", CameraThread.getImage());
            ObjectAnalyse objectAnalyse = new ObjectAnalyse();
            HashMap<Integer, Integer> goods = objectAnalyse.objectDetect("py/open.jpg");
            for (Map.Entry<Integer, Integer> entry : goods.entrySet()) {
                ShelfGoods shelfGoods = new ShelfGoods(entry.getKey() , entry.getValue());
                shelfGoodsRepository.save(shelfGoods);
            }

        } else if (tag.equals("realease")) {
            System.out.println("realease");
            this.closeTime=System.currentTimeMillis();
            System.out.println(closeTime);
            imwrite("py/realease.jpg", CameraThread.getImage());
            ObjectAnalyse objectAnalyse = new ObjectAnalyse();
            HashMap<Integer, Integer> goods = objectAnalyse.objectDetect("py/realease.jpg");
            List<ShelfGoods> shelfGoodsList = shelfGoodsRepository.getAll();
            if( purchaseError(shelfGoodsList,goods) ){
                String cmd = "explorer error.html";
                try {
                    final Process process = Runtime.getRuntime().exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                for (ShelfGoods x : shelfGoodsList) {
                    if (goods.containsKey(x.goodsId)) {
                        if (goods.get(x.goodsId) != x.number) {
                            Shopping shopping = new Shopping(x.goodsId, x.number - goods.get(x.goodsId));
                            shoppingRepository.save(shopping);
                        }
                    } else {
                        Shopping shopping = new Shopping(x.goodsId, x.number);
                        shoppingRepository.save(shopping);
                    }
                }

//                String cmd = "cmd /c start chrome http://localhost:8081/index.html";
//                String cmd = "cmd /c start chrome file:///C:/huogui/%E6%9C%80%E5%90%8E%E7%BB%99%E7%9A%84%E6%BA%90%E7%A0%81/supermarket/index.html";
//                try {
//                    final Process process = Runtime.getRuntime().exec(cmd);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
            flag = 1;
            System.out.println("flag="+flag);
        } else {
            System.out.println("unknown input");
        }

    }

    @RequestMapping(value = "/t")
    public int getFlag(){
        if (flag == 1){
            flag = 0;
            return 1;
        }
        return flag;
    }

    @GetMapping(value = "/shopping/customer")
    public List<ExShopping> getAllShoppingByUserId() {
        try {
            List<Shopping> shoppingList = shoppingRepository.findAll();
            List<ExShopping> exShoppings = new ArrayList<>();
            for (Shopping x : shoppingList) {
                Goods goods = goodsRepository.getGoodsByGoodsId(x.getGoodsId());
                exShoppings.add(new ExShopping(goods.getGoodsName(), x.number, goods.getPrice(), goods.getImgUrl()));
            }
            ExShopping time=new ExShopping();
            time.setGoodsName(getStringTime(this.openTime));
            time.setImgUrl(getStringTime(this.closeTime));
            exShoppings.add(time);
            return exShoppings;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean purchaseError(List<ShelfGoods> open,HashMap<Integer,Integer> close){
        HashMap<Integer,Integer> openMap=new HashMap<>();
        for( ShelfGoods x:open ){
            openMap.put(x.getGoodsId(),x.getNumber());
        }
        for( Map.Entry<Integer,Integer> x:close.entrySet() ){
            if( !openMap.containsKey(x.getKey()) ){
                return true;
            }
            if( openMap.get(x.getKey())<x.getValue() ){
                return true;
            }
        }
        return false;
    }

    public String getStringTime(long time){
        Date date=new Date(time);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
}
