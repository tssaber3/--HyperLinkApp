package com.us.demo.entity.api;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//标签
@Entity
@Table(name = "Tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //标签名
    @Column(name = "name",length = 10)
    private String name;

    @ManyToMany(mappedBy = "tags",cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private transient Set<ApiBasis> apiBases = new HashSet<>();

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ApiBasis> getApiBases() {
        return apiBases;
    }

    public void setApiBases(Set<ApiBasis> apiBases) {
        this.apiBases = apiBases;
    }
}
