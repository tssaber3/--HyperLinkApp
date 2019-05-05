package com.us.demo.service.Impl;

import com.us.demo.dao.RoleRepository;
import com.us.demo.dao.UserRepository;
import com.us.demo.entity.Role;
import com.us.demo.entity.User;
import com.us.demo.mapper.UserMapper;
import com.us.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.selUserByusername(username);
        if(user != null)
        {
            return user;
        }else
        {
            return null;
        }
    }

    //只能添加单个权限  其他的之后在写
    //用户名是否重合  重合就回滚
    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public User insUserAndRole(User user, int roleId) {
        if(roleId >= 1&&roleId <= 3)
        {
            Role role = roleRepository.findOne(roleId);
            if(user.getNickname() == null||user.getUsername() == null||user.getPassword() == null)
            {
                return null;
            }else
            {
                User user1 = userRepository.findByUsername(user.getUsername());
                if(user1 == null)
                {
                    user.getRoles().add(role);
                    role.getUsers().add(user);
                    User user2 = userRepository.save(user);
                    return user2;
                }else
                {
                    return null;
                }

            }
        }else
        {
            return null;
        }

    }
}
