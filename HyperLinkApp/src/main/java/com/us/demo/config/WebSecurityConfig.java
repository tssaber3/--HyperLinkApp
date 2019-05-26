package com.us.demo.config;

import com.us.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Resource
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Resource
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Resource
    private UserDetailService userDetailService;

    @Resource
    private AjaxAccessDeniedHandler deniedHandler;

    @Resource
    private AjaxAuthenticationEntryPoint entryPoint;

    @Resource
    private AuthProvider authProvider;

//    @Resource
//    private AuthProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
//        web.ignoring().antMatchers("/css/**","/js/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //请求授权
                .authorizeRequests()
                //permitAll() 用户均可访问
                .antMatchers("/").permitAll()
                .antMatchers("/statics/**").permitAll()
                //这是一般路人用户可访问的接口
                .antMatchers("/all/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/login").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                //自定义登录
                .formLogin()
//                    .loginPage("/bili-login.html")
                    //登录请求拦截的url,也就是form表单提交时指定的action
                    .loginProcessingUrl("/text/login")
                    //登录成功的拦截器  将token放到response中
                    .successHandler(ajaxAuthenticationSuccessHandler)
                    .failureHandler(ajaxAuthenticationFailureHandler)
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                .logout()
                    .logoutUrl("/text/logout")
                    .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                    .logoutSuccessUrl("/logout/page")
                .invalidateHttpSession(true);//退出后使session无效
        //关闭csrf认证
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//关闭session
        //无权访问返回
        http.exceptionHandling().accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(entryPoint);
    }


    //走自定义认证策略
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
        auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



}
