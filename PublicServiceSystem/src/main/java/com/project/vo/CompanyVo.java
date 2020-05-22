package com.project.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.entity.PublicUserEntity;

import javax.persistence.*;

/**
 * 企业用户实体类
 */

public class CompanyVo {
    /**
     * 企业用户id
     */

    private int id;

    /**
     * 企业名
     */
    private String companyName;
    /*企业登录名*/
    private String companyLoginName;


    /**
     * 企业登录密码
     */

    private String companyLoginPwd;
    /**
     * 企业地址
     */
    private String companyAddress;

    /**
     * 企业联系人
     */
    private String contact;

    /**
     * 企业联系人电话
     */
    private String contactPhone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLoginName() {
        return companyLoginName;
    }

    public void setCompanyLoginName(String companyLoginName) {
        this.companyLoginName = companyLoginName;
    }

    public String getCompanyLoginPwd() {
        return companyLoginPwd;
    }

    public void setCompanyLoginPwd(String companyLoginPwd) {
        this.companyLoginPwd = companyLoginPwd;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
