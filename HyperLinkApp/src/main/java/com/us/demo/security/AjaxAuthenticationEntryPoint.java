package com.us.demo.security;

import com.alibaba.fastjson.JSON;
import com.us.demo.base.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理用户为登录返回给前端的数据
 *
 * @author xiaorui
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 处理用户为登录的返回数据
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(JSON.toJSONString(ApiResponse.ofStatus(ApiResponse.Status.NOT_LOGIN)));
    }
}
