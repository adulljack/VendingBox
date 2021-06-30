package com.graduation.supermarket.Controller;

import com.graduation.supermarket.Entity.User;
import com.graduation.supermarket.Service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username,String password,HttpSession session){
       User user = userService.findUserByNameAndPassword(username,password);
        if(user!=null) {
            session.setAttribute("user", user);
        }else{
            session.setAttribute("info","用户名或密码错误！");
            return "login";
        }
        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
