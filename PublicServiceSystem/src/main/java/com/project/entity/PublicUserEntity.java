package com.project.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**公共用户类*/
@Entity
@Table(name = "t_publicUser")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@DynamicInsert
@DynamicUpdate
public class PublicUserEntity {
    /**用户id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_publicUserId")
    private int id;
    /**用户登录名*/
    @Column(name = "f_loginName")
    private String loginName;
    /**用户密码*/
    @Column(name = "f_pwd")
    private String pwd;
    /**用户类型*/
    @Column(name = "f_userType")
    private int userType;



    public PublicUserEntity() {
    }

    public PublicUserEntity(String loginName, String  pwd, int userType) {
        this.loginName=loginName;
        this.pwd=pwd;
        this.userType=userType;

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
