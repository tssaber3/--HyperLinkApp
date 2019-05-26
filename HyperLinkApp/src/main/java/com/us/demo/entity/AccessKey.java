package com.us.demo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "accesskey")
public class AccessKey{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //调用api的id
    @Column(name = "api_id")
    private int api;

    //创建时间
    @Column(name = "create_time")
    private Timestamp createtime;

    //api使用到期时间 默认7天
    @Column(name = "modify_time")
    private Timestamp modifytime;

    //每天最大使用次数
    @Column(name = "max_use")
    private int maxuse;

    @Column(name = "token")
    private String token;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "AccessKey{" +
                "id=" + id +
                ", api=" + api +
                ", createtime=" + createtime +
                ", modifytime=" + modifytime +
                ", maxuse=" + maxuse +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApi() {
        return api;
    }

    public void setApi(int api) {
        this.api = api;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getModifytime() {
        return modifytime;
    }

    public void setModifytime(Timestamp modifytime) {
        this.modifytime = modifytime;
    }

    public int getMaxuse() {
        return maxuse;
    }

    public void setMaxuse(int maxuse) {
        this.maxuse = maxuse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
