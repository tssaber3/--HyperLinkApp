package com.us.demo.service;

import com.us.demo.entity.AccessKey;
import com.us.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AccessKeyService {

    //添加
    AccessKey insAccessKey(User user,AccessKey accessKey);
}
