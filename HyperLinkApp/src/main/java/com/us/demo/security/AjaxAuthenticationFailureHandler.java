package com.us.demo.security;

import com.google.gson.Gson;
import com.us.demo.util.Singleton;
import com.us.demo.base.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//登录失败时的方法
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private Gson gson = Singleton.INSTANCE.getGson();
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String str = gson.toJson(ApiResponse.ofStatus(ApiResponse.Status.LOGIN_ERR));
        System.out.println(1);
        httpServletResponse.getWriter().write(str);
    }
}
