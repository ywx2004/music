package com.javaclimb.music.dao;

import com.javaclimb.music.domain.SongList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


//歌单dao
@Mapper
@Repository
public interface SongListMapper {
    //增
    public int insert(SongList songList);

    //改
    public int update(SongList songList);

    //删
    public int delete(Integer id);

    //根据主键查询
    public SongList select(Integer id);

    //查询所有歌单
    public List<SongList> selectAll();

    //根据标题模糊查询
    public List<SongList> selectByTitle(String title);

}
