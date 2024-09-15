package com.javaclimb.music.service;

import com.javaclimb.music.domain.Song;

import java.util.List;

//歌曲service接口
public interface SongService {

    //增
    public boolean insert(Song song);

    //改
    public boolean update(Song song);

    //删
    public boolean delete(Integer id);


    //根据主键查询
    public Song select(int id);

    //查询所有歌曲
    public List<Song> selectAll();

    //根据歌曲名模糊查询
    public List<Song> selectByName(String name);
}
