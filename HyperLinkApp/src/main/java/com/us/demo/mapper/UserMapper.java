package com.us.demo.mapper;

import com.us.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;



public interface UserMapper {
    @Select("select * from user " +
            "where username=#{username}")
    User selUserByusername(String username);
}
