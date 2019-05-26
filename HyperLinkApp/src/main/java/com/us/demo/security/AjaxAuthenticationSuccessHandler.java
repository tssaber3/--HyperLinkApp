package com.us.demo.security;

import com.google.gson.Gson;
import com.us.demo.util.Singleton;
import com.us.demo.base.ApiResponse;
import com.us.demo.entity.User;
import com.us.demo.service.Impl.UserServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//登录成功后的
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private UserServiceImpl userService;

    private Gson gson = Singleton.INSTANCE.getGson();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("进入 登录成功拦截器");
        //获取当前用户 的UserDetail对象
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        if (jwtUser == null) {
            System.out.println(jwtUser + "is null");
        }
        User user = userService.findUserByUsername(jwtUser.getUsername());
        //通过UserDetail 对象 的username 从redis中 取出token
        String token = (String) redisTemplate.opsForValue().get(jwtUser.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",user);
        String str = gson.toJson(ApiResponse.ofSuccess(map));
        httpServletResponse.getWriter().write(str);
    }
}
