package com.us.demo.controller;

import com.google.gson.Gson;
import com.us.demo.annotation.PassToken;
import com.us.demo.annotation.UserLoginToken;
import com.us.demo.base.ApiResponse;
import com.us.demo.dao.AccessKeyRepository;
import com.us.demo.dao.ApiBasisRepository;
import com.us.demo.dao.UserRepository;
import com.us.demo.entity.AccessKey;
import com.us.demo.entity.User;
import com.us.demo.entity.api.ApiBasis;
import com.us.demo.service.Impl.AccessKeyServiceImpl;
import com.us.demo.util.JwtUtil;
import com.us.demo.util.Singleton;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private AccessKeyRepository accessKeyRepository;

    @Resource
    private AccessKeyServiceImpl accessKeyService;

    @Resource
    private ApiBasisRepository apiBasisRepository;

    @Resource
    private Gson gson = Singleton.INSTANCE.getGson();
    /**
     * 因为是用户操作 就一定可以通过验证 所以一定有token 并且其中的用户一定存在
     * @param response
     * @param request
     * @param apiId
     * @param maxuse
     * @param endTime
     */
    @UserLoginToken
    @RequestMapping(value = "/getToken")
    public void getToken(HttpServletResponse response,HttpServletRequest request,
                         @RequestParam("apiId")int apiId,
                         @RequestParam("maxuse")int maxuse,
                         @RequestParam("endTime")String endTime) throws IOException {
        PrintWriter out = response.getWriter();
        AccessKey accessKey = new AccessKey();
        String username = "";
        String token = request.getHeader("token");
        //到期时间
        Timestamp modifyTime = Timestamp.valueOf(endTime);
        Map<String,Object> claims = new HashMap<>();
        claims = JwtUtil.parseJWT(token);
        username = (String) claims.get("username");
//        for (Map.Entry<String,Object> entry:claims.entrySet()) {
//            if(entry.getKey().equals("username")) {
//                username = (String) entry.getValue();
//            }
//        }
        User user = userRepository.findByUsername(username);
        accessKey.setApi(apiId);
        accessKey.setModifytime(modifyTime);
        accessKey.setMaxuse(maxuse);
        AccessKey accessKey1 = accessKeyService.insAccessKey(user,accessKey);
        String str = gson.toJson(ApiResponse.ofSuccess(accessKey1));
        out.print(str);
        out.flush();
    }

    @UserLoginToken
    @RequestMapping(value = "/getToken1")
    public void getToken1()
    {
        System.out.println("asdasd");
    }

    @UserLoginToken
    @PostMapping("/textaApi")
    public void textaApi(HttpServletResponse response,HttpServletRequest request,
                         @RequestParam("apiToken")String apiToken,@RequestParam("args")String args) throws IOException {
        RestTemplate restTemplate = Singleton.INSTANCE.getRestTemplate();
        PrintWriter out = response.getWriter();
        Claims claims = JwtUtil.parseJWT(apiToken);
        ApiBasis apiBasis = apiBasisRepository.findOne((Integer) claims.get("api_id"));
        String str = restTemplate.getForObject(apiBasis.getAddress(),String.class);
        System.out.println(str);
    }

}
