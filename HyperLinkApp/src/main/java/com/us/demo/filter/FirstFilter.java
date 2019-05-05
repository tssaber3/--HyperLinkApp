package com.us.demo.filter;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//整合filter方式一
@WebFilter("/token/*")
public class FirstFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) arg1;
        HttpServletRequest request = (HttpServletRequest) arg0;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "token");
//        放行OPTIONS请求
        if(request.getMethod().equals(RequestMethod.OPTIONS.name()))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        System.out.println("进入 ");
        arg2.doFilter(arg0,arg1);
        System.out.println("离开");
    }

    @Override
    public void destroy() {

    }
}
