package com.us.demo.service.Impl;

import com.us.demo.dao.AccessKeyRepository;
import com.us.demo.entity.AccessKey;
import com.us.demo.entity.User;
import com.us.demo.service.AccessKeyService;
import com.us.demo.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class AccessKeyServiceImpl implements AccessKeyService {
    @Resource
    private AccessKeyRepository accessKeyRepository;

    @Override
    public AccessKey insAccessKey(User user, AccessKey accessKey) {
        //创建时间
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        createTime = Timestamp.valueOf(time);
        accessKey.setCreatetime(createTime);
        String keyToken = JwtUtil.createJwt(user,accessKey);
        accessKey.setToken(keyToken);
        accessKey.setUser(user);
        user.getAccessKeys().add(accessKey);
        return accessKeyRepository.save(accessKey);
    }
}
