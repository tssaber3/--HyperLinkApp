package com.us.demo.entity.api;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "api_basis")
public class ApiBasis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //api 的名字
    @Column(name = "name",length = 50)
    private String name;

    //简介
    @Column(name = "simple_introduce",length = 255)
    private String simpleintroduce;

    //地址
    @Column(name = "address",length = 50)
    private String address;

    //返回格式
    @Column(name = "format",length = 100)
    private String format;

    //请求方式
    @Column(name = "request_way",length = 20)
    private String requestway;

    //返回示例
    @Column(name = "back_example",length = 100)
    private String backexample;

    //参数描述
    @Column(name = "parameter_description",length = 255)
    private String parameterdescription;

    //付费类型 1是收费 0是免费
    @Column(name = "pay_type")
    private int paytype;

    //价格
    @Column(name = "price")
    private Double price;

    private transient ErrorCode errorCode;

    private transient ProductBasis productBasis;

    //api的类型
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "api_type")
    private Type type;

    //api的标签
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinTable(name = "t_api_tag",joinColumns = @JoinColumn(name = "api_id"),inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Override
    public String toString() {
        return "ApiBasis{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", simpleintroduce='" + simpleintroduce + '\'' +
                ", address='" + address + '\'' +
                ", format='" + format + '\'' +
                ", requestway='" + requestway + '\'' +
                ", backexample='" + backexample + '\'' +
                ", parameterdescription='" + parameterdescription + '\'' +
                ", paytype=" + paytype +
                ", price=" + price +
                ", errorCode=" + errorCode +
                ", productBasis=" + productBasis +
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

    public String getSimpleintroduce() {
        return simpleintroduce;
    }

    public void setSimpleintroduce(String simpleintroduce) {
        this.simpleintroduce = simpleintroduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRequestway() {
        return requestway;
    }

    public void setRequestway(String requestway) {
        this.requestway = requestway;
    }

    public String getBackexample() {
        return backexample;
    }

    public void setBackexample(String backexample) {
        this.backexample = backexample;
    }

    public String getParameterdescription() {
        return parameterdescription;
    }

    public void setParameterdescription(String parameterdescription) {
        this.parameterdescription = parameterdescription;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ProductBasis getProductBasis() {
        return productBasis;
    }

    public void setProductBasis(ProductBasis productBasis) {
        this.productBasis = productBasis;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
