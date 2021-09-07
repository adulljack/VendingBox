package com.graduation.supermarket.Controller;

import com.github.pagehelper.PageInfo;
import com.graduation.supermarket.Entity.*;
import com.graduation.supermarket.Repository.ShoppingRepository;
import com.graduation.supermarket.Service.GoodsService;
import com.graduation.supermarket.Service.OrderService;
import com.graduation.supermarket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("order")
@CrossOrigin
public class OrderController {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("pay")
    @ResponseBody
    public String pay(){
        List<Shopping> shoppingList = shoppingRepository.findAll();

        double totalFee = 0;
        Orders order = new Orders();
        String currentDateString = DateUtil.getCurrentDateStr();
        String currentDateStr = currentDateString.substring(5, 15);
        for (Shopping  shopping: shoppingList) {
            OrderGoods orderGoods = new OrderGoods();
            //根据id查询商品
            Goods good = goodsService.findById(shopping.getGoodsId());
            totalFee += shopping.getNumber()*good.getPrice();
            //
            orderGoods.setGoodsId(good.getGoodsId());
            orderGoods.setOrderId(currentDateStr);
            orderService.saveInOrderGoods(orderGoods);
        }
        order.setId(currentDateStr);
        order.setTotalFee(new BigDecimal(totalFee));
        order.setStatus(0);
        //生成订单
        orderService.save(order);
        //库存减1
//        for (Shopping shopping : shoppingList) {
//            goodsService.updateNumById(shopping.getGoodsId(),shopping.getNumber());
//        }
        return order.getId();
    }

    @RequestMapping("setStatus")
    public String setStatus(String orderId){

        Orders order = orderService.findById(orderId);
        order.setStatus(1);
        orderService.updateById(order);
        return "success";
    }

    @RequestMapping("findAll/{current}/{size}")
    public String findAll(@PathVariable(value = "current",required = false) Integer current, @PathVariable(value = "size",required = false) Integer size, HttpServletRequest request){
        if (StringUtils.isEmpty(current) && StringUtils.isEmpty(size)) {
            current = 1;
            size = 5;
        }
        List<OrderViewObject> orderList = orderService.findAll(current, size);
        PageInfo<OrderViewObject> pageInfo = new PageInfo<>(orderList);
        request.setAttribute("pageInfo",pageInfo);
        return "ordersList";
    }
}
