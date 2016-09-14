package org.lyh.validate;

import java.sql.Timestamp;

/**
 * Created by lvyahui on 2015-06-26.
 */

public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private Integer gold;
    private Integer progress;
    private Timestamp createdAt;
    private String email;
    private String phone;
    private String site;
    // 特殊表单数据
    private String rePassword;


    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }


    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String[][] rules() {
        return new String [][] {
                {"username,password,email,gold,progress,phone","required"},
                {"username,password","length","6","14"},
                {"rePassword","equals","password"},
                {"email","email","\\w{6,12}"},
                {"createdAt","timestamp"},
                {"phone","number"},
                {"site","url"}
        };
    }

    public String[][] labels() {
        return new String[][]{
                {"username","用户名"},
                {"password","密码"},
                {"rePassword","确认密码"},
                {"email","邮箱"},
                {"progress","进度"},
                {"phone","电话"},
                {"gold","金币"}
        };
    }

}
