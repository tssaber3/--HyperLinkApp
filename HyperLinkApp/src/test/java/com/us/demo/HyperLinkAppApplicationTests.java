package com.us.demo;

import com.google.gson.Gson;
import com.us.demo.util.CommonRpc;
import com.us.demo.util.Message;
import com.us.demo.dao.RoleRepository;
import com.us.demo.dao.UserRepository;
import com.us.demo.entity.Role;
import com.us.demo.entity.User;
import com.us.demo.service.Impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


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

    @Test
    public void text()
    {
        User user = new User();
        user.setNickname("用户4");
        user.setUsername("666666");
        user.setPassword("123456");

        System.out.println(userService.insUserAndRole(user,2));

    }

    @Test
    public void text1()
    {
        CommonRpc commonRpc = new CommonRpc();
        String result = commonRpc.getMessage("15922720907","2457");
        Message message = gson.fromJson(result,Message.class);
        System.out.println(message.toString());

    }


    @Test
    public void contextLoads() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();
//        role1.setId(1);
        role1.setName("ROLE_ADMIN");
//        role2.setId(2);
        role2.setName("ROLE_USER");
        role3.setName("ROLE_BLACK");
        user1.setNickname("管理员");
        user1.setUsername("11111");
        user1.setPassword("123456");
        user2.setNickname("用户");
        user2.setUsername("22222");
        user2.setPassword("123456");
        user3.setNickname("黑名单");
        user3.setPassword("123456");
        user3.setUsername("333333");


        role1.getUsers().add(user1);
        user1.getRoles().add(role1);

//        role1.getUsers().add(user2);
//        user2.getRoles().add(role1);

        role2.getUsers().add(user2);
        user2.getRoles().add(role2);

        role3.getUsers().add(user3);
        user3.getRoles().add(role3);

        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }

    /**
     * $2a$10$3.mjbbY94OBqTcXNlrZix.IjGjVUO7u51UtYFKaSwBR4EbrF4E5eG
     * $2a$10$UWm44GcmVFvH1mx5bYlZ/.tpjHcV1kmE/aY/p5nhEQkb0NNyQgjBS
     * $2a$10$bVZEKPN5zc6ZeTUc1mSbW.gGbsOmTMPdYBWeFrX6Xm6xTLxyOo35K
     * $2a$10$V8GC0IiXxuzvbJ0Szs1TEe9wzdbopbCjtSBpqnkzfRt8vAFB3gGq6
     *
     */
    @Test
    public void text2()
    {

    }



}
