package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


//歌曲dao
@Mapper
@Repository
public interface SongMapper {
    //增
    public int insert(Song song);

    //改
    public int update(Song song);

    //删
    public int delete(Integer id);

    //根据主键查询歌曲
    public Song select(Integer id);

    //查询所有歌曲
    public List<Song> selectAll();

    //根据歌名模糊查询
    public List<Song> selectByName(String name);

}
