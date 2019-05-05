package com.us.demo.security;

import com.us.demo.util.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/*
 * 自定义认证
 * 这个比AjaxAuthenticationSuccessHandler先执行
 */
@Component
public class AuthProvider implements AuthenticationProvider {

    @Resource
    private UserDetailService userDetailService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //登录时需要通过  将用户信息存放在redis中
    //这个在loadUserByUsername之前运行
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户名(邮箱)和密码
         String username = authentication.getName();
         String password = (String) authentication.getCredentials();
         JwtUser jwtUser = (JwtUser) userDetailService.loadUserByUsername(username);

        if (jwtUser == null)
        {
            throw new AuthenticationCredentialsNotFoundException("not find user");
        }

        //密码验证  将用户信息存放在redis中
        if (bCryptPasswordEncoder.matches(password,jwtUser.getPassword()))
        {
            String token = JwtUtil.createJwt(500 * 1000, jwtUser);
            System.out.println("密码正确");
            //设置5000秒过期
            stringRedisTemplate.opsForValue().set(jwtUser.getUsername(),token,5000, TimeUnit.SECONDS);
            return new UsernamePasswordAuthenticationToken(jwtUser,null,jwtUser.getAuthorities());
        }else
        {
            throw new BadCredentialsException("password error");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
