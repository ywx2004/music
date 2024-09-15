package com.javaclimb.music.domain;

import java.io.Serializable;

//歌单
public class SongList implements Serializable {
    //主键id
    private Integer id;
    //标题
    private String title;
    //简介
    private String introduction;
    //图片
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }





}
