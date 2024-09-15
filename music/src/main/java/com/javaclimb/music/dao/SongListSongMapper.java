package com.javaclimb.music.dao;

import com.javaclimb.music.domain.SongListSong;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


//歌单歌曲dao
@Mapper
@Repository
public interface SongListSongMapper {
    //增
    public int insert(SongListSong songListSong);

    //改
    public int update(SongListSong songListSong);

    //删
    public int delete(Integer id);
    public int deleteBySongId(Integer songId,Integer songListId);

    //根据主键查询歌曲
    public SongListSong select(Integer id);

    //查询所有歌单歌曲
    public List<SongListSong> selectAll();

    //根据歌单id查询所有的歌曲
    public List<SongListSong> selectBySongListId(Integer songListId);

}
