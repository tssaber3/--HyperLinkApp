package com.us.demo.entity.api;

import javax.persistence.*;

@Entity
@Table(name = "product_basis")
public class ProductBasis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //api的编号
    @Column(name = "api_id")
    private int apiId;

    //功能介绍
    @Column(name = "product_features",length = 255)
    private String features;

    //功能示例
    @Column(name = "function_example",length = 255)
    private String example;

    //业务场景
    @Column(name = "business_scene",length = 255)
    private String scene;

    @Override
    public String toString() {
        return "ProductBasis{" +
                "id=" + id +
                ", apiId=" + apiId +
                ", features='" + features + '\'' +
                ", example='" + example + '\'' +
                ", scene='" + scene + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }
}
