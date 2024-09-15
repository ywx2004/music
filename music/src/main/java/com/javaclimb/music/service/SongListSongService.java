package com.javaclimb.music.service;

//歌单歌曲service接口

import com.javaclimb.music.domain.SongListSong;

import java.util.List;

public interface SongListSongService {
    //增
    public boolean insert(SongListSong songListSong);

    //改
    public boolean update(SongListSong songListSong);

    //删
    public boolean delete(Integer id);
    public boolean deleteBySongId(Integer songId,Integer songListId);

    //根据主键查询歌曲
    public SongListSong select(Integer id);

    //查询所有歌单歌曲
    public List<SongListSong> selectAll();

    //根据歌单id查询所有的歌曲
    public List<SongListSong> selectBySongListId(Integer songListId);

}
