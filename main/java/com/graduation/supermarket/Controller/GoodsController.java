package com.graduation.supermarket.Controller;

import com.github.pagehelper.PageInfo;
import com.graduation.supermarket.Entity.Goods;
import com.graduation.supermarket.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/findAll/{current}/{size}")
    public String findAll(@PathVariable(value = "current",required = false) Integer current, @PathVariable(value = "size",required = false) Integer size, HttpServletRequest request){
        if (StringUtils.isEmpty(current) && StringUtils.isEmpty(size)) {
            current = 1;
            size = 5;
        }
        List<Goods> goodsList = goodsService.findAll(current, size);
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        request.setAttribute("pageInfo",pageInfo);
        return "goodsList";
    }

    @RequestMapping("add")
    public String updateById(Goods goods, HttpServletRequest request, MultipartFile upload){
        if(StringUtils.isEmpty(request.getParameter("goodsId"))){
            upload(upload,request);
            String imgStr = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+upload.getOriginalFilename();
            goods.setImgUrl(imgStr);
            //添加
            goodsService.addGood(goods);
        }else {
            upload(upload,request);
            String imgStr = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+upload.getOriginalFilename();
            goods.setImgUrl(imgStr);
            goodsService.updateById(goods);
        }
        return "redirect:/goods/findAll/1/5";
    }

    //上传图片
    private void upload(MultipartFile upload,HttpServletRequest request){
        String fileName = upload.getOriginalFilename();
        String realPath = request.getServletContext().getRealPath("/");
        String path = realPath.substring(0, realPath.lastIndexOf("webapp"))+"\\resources\\static\\";
        String finalPath = path + fileName;
        File file = new File(finalPath);
        if(!file.exists() && !file.isAbsolute()){
            file.mkdir();
        }
        try {
            upload.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }
    }

    @RequestMapping("deleteById")
    public String deleteById(Integer[] ids){
        for (Integer id : ids) {
            goodsService.deleteById(id);
        }
        return "redirect:/goods/findAll/1/5";
    }

    @RequestMapping("selectLike")
    public String selectLike(HttpServletRequest request,String word, @RequestParam(value = "current",required = false) Integer current, @RequestParam(value = "size",required = false) Integer size){

        if (StringUtils.isEmpty(current) && StringUtils.isEmpty(size)) {
            current = 1;
            size = 5;
        }
        List<Goods> goods = goodsService.selectLike(word,current, size);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        request.setAttribute("pageInfo",pageInfo);
        return "goodsList";
    }

    @RequestMapping("findById")
    public String findById(Integer goodsId,HttpServletRequest request){
        Goods good = goodsService.findById(goodsId);
        request.setAttribute("good",good);
        return "goodsAdd";
    }
}
