package com.javaclimb.music.domain;

import java.io.Serializable;

//歌单歌曲

public class SongListSong implements Serializable {
    //主键id
    private Integer id;
    //歌曲id
    private Integer songId;
    //歌单id
    private Integer songListId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }
}
