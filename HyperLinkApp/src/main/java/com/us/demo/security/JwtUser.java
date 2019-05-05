package com.us.demo.security;

import com.us.demo.entity.Role;
import com.us.demo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtUser implements UserDetails {


    private int id;
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    //写一个能直接使用user创建的JwtUser的构造器
    //并且将角色装配到 对应user之中
    public JwtUser(User user)
    {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        List<Role> list = new ArrayList<>(user.getRoles());
        System.out.println(list);
        for (Role role:list)
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }


    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<SimpleGrantedAuthority> getAuthorityList()
    {
        return authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
