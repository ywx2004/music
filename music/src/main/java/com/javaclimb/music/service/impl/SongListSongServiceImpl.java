package com.javaclimb.music.service.impl;

//歌单歌曲service实现类

import com.javaclimb.music.dao.SongListSongMapper;
import com.javaclimb.music.domain.SongListSong;
import com.javaclimb.music.service.SongListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListSongServiceImpl implements SongListSongService {
    @Autowired
    private SongListSongMapper songListSongMapper;
    @Override
    public boolean insert(SongListSong songListSong) {
        return songListSongMapper.insert(songListSong) > 0;
    }

    @Override
    public boolean update(SongListSong songListSong) {
        return songListSongMapper.update(songListSong) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return songListSongMapper.delete(id) > 0;
    }

    @Override
    public boolean deleteBySongId(Integer songId,Integer songListId){
        return songListSongMapper.deleteBySongId(songId,songListId)>0;
    }

    @Override
    public SongListSong select(Integer id) {
        return songListSongMapper.select(id);
    }

    @Override
    public List<SongListSong> selectAll() {
        return songListSongMapper.selectAll();
    }

    @Override
    public List<SongListSong> selectBySongListId(Integer songListId) {
        return songListSongMapper.selectBySongListId(songListId);
    }
}
