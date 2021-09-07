package com.graduation.supermarket.Controller;

import com.github.pagehelper.PageInfo;
import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Entity.Shopping;
import com.graduation.supermarket.Service.GoodsService;
import com.graduation.supermarket.Service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @RequestMapping("/findAll/{current}/{size}")
    public String findAll(@PathVariable(value = "current",required = false) Integer current, @PathVariable(value = "size",required = false) Integer size, HttpServletRequest request){
        if (StringUtils.isEmpty(current) && StringUtils.isEmpty(size)) {
            current = 1;
            size = 5;
        }
        List<Shopping> shoppingList = shoppingService.findAll(current, size);
        PageInfo<Shopping> pageInfo = new PageInfo<>(shoppingList);
        request.setAttribute("pageInfo",pageInfo);
        return "shoppingList";
    }


}
