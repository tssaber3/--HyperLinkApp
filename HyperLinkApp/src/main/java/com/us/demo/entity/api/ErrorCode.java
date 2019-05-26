package com.us.demo.entity.api;

import javax.persistence.*;

@Entity
@Table(name = "error_code")
public class ErrorCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //api的编号
    @Column(name = "api_id")
    private int apiId;

    //服务级错误，储存json格式
    @Column(name = "service_error",length = 255)
    private String serviceerror;

    //系统级错误，储存json格式
    @Column(name = "system_error",length = 255)
    private String systemerror;

    //错误格式，储存json格式
    @Column(name = "error_fomat",length = 255)
    private String errorfomat;


    @Override
    public String toString() {
        return "ErrorCode{" +
                "id=" + id +
                ", apiId=" + apiId +
                ", serviceerror='" + serviceerror + '\'' +
                ", systemerror='" + systemerror + '\'' +
                ", errorfomat='" + errorfomat + '\'' +
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

    public String getServiceerror() {
        return serviceerror;
    }

    public void setServiceerror(String serviceerror) {
        this.serviceerror = serviceerror;
    }

    public String getSystemerror() {
        return systemerror;
    }

    public void setSystemerror(String systemerror) {
        this.systemerror = systemerror;
    }

    public String getErrorfomat() {
        return errorfomat;
    }

    public void setErrorfomat(String errorfomat) {
        this.errorfomat = errorfomat;
    }
}
