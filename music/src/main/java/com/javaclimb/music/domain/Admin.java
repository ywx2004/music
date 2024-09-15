package com.javaclimb.music.domain;


// 管理员

import java.io.Serializable;

public class Admin implements Serializable {
    //主键/id
    private Integer id;
    //昵称
    private String name;
    //密码
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
