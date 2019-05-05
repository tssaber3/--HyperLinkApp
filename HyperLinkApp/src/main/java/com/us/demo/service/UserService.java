package com.us.demo.service;

import com.us.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findUserByUsername(String username);

    //参数 第一个用户  第二个权限的id  1管理员 2普通用户 3黑名单
    User insUserAndRole(User user,int roleId);

}
