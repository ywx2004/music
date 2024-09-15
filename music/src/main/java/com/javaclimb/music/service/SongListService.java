package com.javaclimb.music.service;

//歌单service接口

import com.javaclimb.music.domain.SongList;

import java.util.List;

public interface SongListService {
    //增
    public boolean insert(SongList songList);

    //改
    public boolean update(SongList songList);

    //删
    public boolean delete(Integer id);

    //根据主键查询
    public SongList select(Integer id);

    //查询所有歌单
    public List<SongList> selectAll();

    //根据标题模糊查询
    public List<SongList> selectByTitle(String title);
}
