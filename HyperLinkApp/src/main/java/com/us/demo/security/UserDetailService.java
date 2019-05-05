package com.us.demo.security;

import com.us.demo.dao.UserRepository;
import com.us.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserDetailService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //每次调用就要刷新token
    //s 可能是username 后者 email
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("进入自定义认证协议");
        if (s == null)
        {
            return null;
        }
        User user1 = userRepository.findByUsername(s);
        User user2 = userRepository.findByEmail(s);
        //如果用户没找到
        if(user1 != null)
        {
            //添加用户权限
            JwtUser jwtUser = new JwtUser(user1);
            return  (UserDetails) jwtUser;
        }else if(user2 != null)
        {
            //添加用户权限
            JwtUser jwtUser = new JwtUser(user2);
            return  (UserDetails) jwtUser;
        }else
        {
            logger.info("没找到用户");
            return null;
        }
    }
}
