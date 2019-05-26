package com.us.demo;

import com.google.gson.Gson;
import com.us.demo.dao.*;
import com.us.demo.entity.AccessKey;
import com.us.demo.entity.api.ApiBasis;
import com.us.demo.entity.api.Tag;
import com.us.demo.entity.api.Type;
import com.us.demo.util.JwtUtil;
import com.us.demo.entity.User;
import com.us.demo.service.Impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HyperLinkAppApplication.class)
public class HyperLinkAppApplicationTests {

    private Gson gson = new Gson();

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private AccessKeyRepository accessKeyRepository;

    @Resource
    private ApiBasisRepository apiBasisRepository;

    @Resource
    private TagRepository tagRepository;

    @Resource
    private TypeRepository typeRepository;

//    @Test
//    public void text()
//    {//密码加密
//        String encodePass = bCryptPasswordEncoder.encode("123456");
//        User user2 = new User();
//        user2.setNickname("用户2");
//        user2.setUsername("444444");
//        user2.setPassword(encodePass);
//        User user = new User();
//        user.setNickname("用户3");
//        user.setUsername("55555");
//        user.setPassword(encodePass);
//        User user1 = new User();
//        user1.setNickname("用户4");
//        user1.setUsername("666666");
//        user1.setPassword(encodePass);
//        userService.insUserAndRole(user2,2);
//
//        System.out.println(userService.insUserAndRole(user,2));
//        userService.insUserAndRole(user,2);
//
//    }
//
//    @Test
//    public void text1()
//    {
//        CommonRpc commonRpc = new CommonRpc();
//        String result = commonRpc.getMessage("15922720907","2457");
//        Message message = gson.fromJson(result,Message.class);
//        System.out.println(message.toString());
//
//    }


//    @Test
//    public void contextLoads() {
//        User user1 = new User();
//        User user2 = new User();
//        User user3 = new User();
//        Role role1 = new Role();
//        Role role2 = new Role();
//        Role role3 = new Role();
//
//        //密码加密
//        String encodePass = bCryptPasswordEncoder.encode("123456");
////        role1.setId(1);
//        role1.setName("ROLE_ADMIN");
////        role2.setId(2);
//        role2.setName("ROLE_USER");
//        role3.setName("ROLE_BLACK");
//        user1.setNickname("管理员");
//        user1.setUsername("11111");
//        user1.setPassword(encodePass);
//        user2.setNickname("用户");
//        user2.setUsername("22222");
//        user2.setPassword(encodePass);
//        user3.setNickname("黑名单");
//        user3.setPassword(encodePass);
//        user3.setUsername("333333");
//
//
//        role1.getUsers().add(user1);
//        user1.getRoles().add(role1);
//
////        role1.getUsers().add(user2);
////        user2.getRoles().add(role1);
//
//        role2.getUsers().add(user2);
//        user2.getRoles().add(role2);
//
//        role3.getUsers().add(user3);
//        user3.getRoles().add(role3);
//
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//        roleRepository.save(role3);
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//
//    }

    /**
     * $2a$10$3.mjbbY94OBqTcXNlrZix.IjGjVUO7u51UtYFKaSwBR4EbrF4E5eG
     * $2a$10$UWm44GcmVFvH1mx5bYlZ/.tpjHcV1kmE/aY/p5nhEQkb0NNyQgjBS
     * $2a$10$bVZEKPN5zc6ZeTUc1mSbW.gGbsOmTMPdYBWeFrX6Xm6xTLxyOo35K
     * $2a$10$V8GC0IiXxuzvbJ0Szs1TEe9wzdbopbCjtSBpqnkzfRt8vAFB3gGq6
     *
     */
    @Test
    public void text2() {
        AccessKey accessKey = new AccessKey();
        accessKey.setApi(1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        timestamp = Timestamp.valueOf(time);
        accessKey.setCreatetime(timestamp);
        accessKey.setMaxuse(100);
        User user = userRepository.findByUsername("444444");
        String token = JwtUtil.createJwt(user,accessKey);
        accessKey.setToken(token);
        user.getAccessKeys().add(accessKey);
        accessKey.setUser(user);
        accessKeyRepository.save(accessKey);

    }
    @Test
    public void text3() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJwdWJsaWNfa2V5IjoiYWJjMTIzNCIsImFwaV9pZCI6MSwic3ViIjoiNDQ0NDQ0IiwidXNlcl9pZCI6NCwiaWF0IjoxNTU4MjU1MjM5LCJqdGkiOiJhYjYzNDdjOS1lOTBkLTQ4MmUtYmQwNi05NzhiNTcwZjg4NmQifQ.AqCojHxZYGERpJZ-MZQHaCOFB9JdpEoGs2uUFOFEdxU";
        Map<String,Object> claims = new HashMap<>();
        claims = JwtUtil.parseJWT(token);
        for (Map.Entry<String,Object> entry:claims.entrySet())
        {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Test
    public void test4(){
        Calendar nowtime = Calendar.getInstance();
        nowtime.add(Calendar.DATE,3);
        Date exp = nowtime.getTime();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exp);
        System.out.println(time);
    }

    @Test
    public void test5()
    {
        //创建标签
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        tag1.setName("免费");
        tag2.setName("测试");
        tag3.setName("科技");
        Type type = new Type();
        type.setName("人脸识别");
        ApiBasis apiBasis = new ApiBasis();
        apiBasis.setName("测试");
        apiBasis.setAddress("http://47.106.211.72:8080/api/v1/text");
        apiBasis.setFormat("这是测试格式");
        apiBasis.setRequestway("这是返回格式");
        apiBasis.setRequestway("GET");
        apiBasis.setBackexample("这是返回示例");
        apiBasis.setParameterdescription("无序参数");
        apiBasis.setSimpleintroduce("这是一个简单的描述");
        apiBasis.setPaytype(0);
        apiBasis.setPrice(0.0);
        apiBasis.setType(type);

        tag1.getApiBases().add(apiBasis);
        tag2.getApiBases().add(apiBasis);
        tag3.getApiBases().add(apiBasis);

        apiBasis.getTags().add(tag1);
        apiBasis.getTags().add(tag2);
        apiBasis.getTags().add(tag3);

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

        typeRepository.save(type);

        apiBasisRepository.save(apiBasis);
    }



}
