package com.javaclimb.music.domain;

import java.io.Serializable;

public class Mv implements Serializable {
    // 主键id
    private Integer id;
    // 名字
    private String name;
    // 简介
    private String introduction;
    // 图片地址
    private String picture;
    // 视频地址
    private String url;

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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
