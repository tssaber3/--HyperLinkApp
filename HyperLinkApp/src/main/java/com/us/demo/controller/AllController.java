package com.us.demo.controller;

import com.google.gson.Gson;
import com.us.demo.util.CommonRpc;
import com.us.demo.util.Message;
import com.us.demo.util.Singleton;
import com.us.demo.base.ApiResponse;
import com.us.demo.base.ServiceResult;
import com.us.demo.entity.User;
import com.us.demo.service.Impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@RestController
@RequestMapping("/all")
public class AllController {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Gson gson = Singleton.INSTANCE.getGson();

    /**
     * 用于注册  只需要昵称 用户名（手机号） 密码 其他的之后在完善信息
     * @param response
     * @param request
     * @param nickname
     * @param username
     * @param password
     */
    @RequestMapping("/register")
    public void register(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam("nickname")String nickname,
                         @RequestParam("username")String username,
                         @RequestParam("password")String password) throws IOException {
        PrintWriter out = response.getWriter();
        String encodePass = bCryptPasswordEncoder.encode(password);
        User user = new User(username,encodePass,nickname);
        User user1 = userService.insUserAndRole(user,2);
        if(user1 != null)
        {
            String str = gson.toJson(ServiceResult.of(user1));
            out.print(str);
        }else
        {
            String str = gson.toJson(ServiceResult.notSuccess());
            out.print(str);
        }
        out.flush();
    }

    /*
     *  获取验证码
     * @param request
     * @param response
     * @param username:用户的电话号码
     */
    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request,HttpServletResponse response,
                        @RequestParam("username")String username) throws IOException {
        PrintWriter out = response.getWriter();
        Random random = new Random();
        CommonRpc commonRpc = new CommonRpc();
        int code = random.nextInt(10000);
        String data = commonRpc.getMessage(username,code + "");
        Message message = gson.fromJson(data,Message.class);
        if (message.getCode().equals("OK")) {
            String str = gson.toJson(ApiResponse.ofSuccess(code));
            out.print(str);
        }else {
            String str = gson.toJson(ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM));
            out.print(str);
        }
    }

    @RequestMapping("/log")
    public void log() {

    }
}
