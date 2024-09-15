package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.SongMapper;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//歌曲service实现类
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;


    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }



    @Override
    public Song select(int id) {
        return songMapper.select(id);
    }

    @Override
    public List<Song> selectAll() {
        return songMapper.selectAll();
    }

    @Override
    public List<Song> selectByName(String name) {
        return songMapper.selectByName(name);
    }
}
