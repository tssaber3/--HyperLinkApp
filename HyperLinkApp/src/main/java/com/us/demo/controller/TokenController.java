package com.us.demo.controller;

import com.google.gson.Gson;
import com.us.demo.dao.UserRepository;
import com.us.demo.util.Singleton;
import com.us.demo.annotation.PassToken;
import com.us.demo.annotation.UserLoginToken;
import com.us.demo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource
    private UserRepository userRepository;

    @Resource

    private Gson gson = Singleton.INSTANCE.getGson();

    @RequestMapping("/login")
    public void login(HttpServletResponse response,HttpServletRequest request,
                      @RequestParam("username")String username,
                      @RequestParam("password")String password) throws IOException {
        PrintWriter out = response.getWriter();
        User user = userRepository.findByUsername(username);
        System.out.println(username);
        if(user == null) {
            System.out.println("用户不存在");
        }else {
            if(!(user.getPassword().equals(password))) {
                System.out.println("密码不对");
            }
            else {
//                String token = tokenService.getToken(user);
//
//                System.out.println(user);
//
//                String str = gson.toJson(token);
//                out.print(str);
//                out.flush();
            }
        }
    }
    @UserLoginToken
    @RequestMapping("/getMessage")
    public void getMessage(HttpServletResponse response,HttpServletRequest request) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("霞之丘诗羽");
        out.flush();
    }

    @PassToken
    @RequestMapping("/pass")
    public void pass(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("成功");
        out.flush();
    }

    //注册时需要昵称(nickname)  密码 手机号(作为username)  短信验证码
}
