package com.us.demo.security;

import com.alibaba.fastjson.JSON;
import com.us.demo.util.JwtUtil;
import com.us.demo.base.ApiResponse;
import com.us.demo.entity.User;
import com.us.demo.service.Impl.UserServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//退出成功时的拦截器
//将token从redis中删除
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("token");
        Map<String,Object> claims = JwtUtil.parseJWT(token);
        if(claims == null)
        {
            httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofStatus(ApiResponse.Status.NOT_LOGIN)));
        }
        User user = userService.findUserByUsername((String) claims.get("username"));
        redisTemplate.delete(user.getUsername());
        httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofSuccess(null)));
    }
}
