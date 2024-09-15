package com.javaclimb.music.dao;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
//管理员dao
@Mapper
@Repository
public interface AdminMapper {
    //验证密码是否正确
    public int verifyPassword(String username, String password);
}
