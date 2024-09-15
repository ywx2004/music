package com.javaclimb.music.controller;

//歌曲管理controller

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import com.javaclimb.music.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    //添加歌曲
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
        JSONObject jsonObject=new JSONObject();

        //获取前端传来的参数
        String name=request.getParameter("name").trim();//歌名
        String introduction=request.getParameter("introduction").trim();//简介
        String picture="/img/songPic/images.jpg";//默认图片
        String lyric=request.getParameter("lyric").trim();//歌词

        //上传歌曲文件
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲上传失败");
            return jsonObject;
        }

        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+mpFile.getOriginalFilename();

        //文件路径
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"song";

        //如果文件路径不存在，新增该路径
        File file1=new File(filePath);
        if(!file1.exists()){
            file1.mkdirs();
        }

        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);

        //储存到数据库里的相对文件地址
        String storeUrlPath="/song/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song=new Song();
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPicture(picture);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag=songService.insert(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
                jsonObject.put("avatar",storeUrlPath);
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    //修改歌曲
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String name=request.getParameter("name").trim();
        String introduction=request.getParameter("introduction").trim();
        String lyric=request.getParameter("lyric").trim();

        //保存到歌曲的对象中
        Song song=new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        boolean flag=songService.update(song);
        if(flag){
            //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        //保存失败
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    //删除歌曲
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<DeleteResponse> deleteSong(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        boolean success = songService.delete(Integer.parseInt(id));

        // 创建响应对象
        DeleteResponse response = new DeleteResponse();
        response.setSuccess(success);
        response.setMessage(success ? "Deletion successful" : "Deletion failed");

        // 返回 ResponseEntity，自动将 DeleteResponse 对象序列化为 JSON
        return ResponseEntity.ok(response);
    }

    // 创建一个响应对象类
    public static class DeleteResponse {
        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    //根据主键查询整个对象
    @RequestMapping(value = "/select",method=RequestMethod.GET)
    public Object selectSong(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        return songService.select(Integer.parseInt(id));
    }

    //查询所有对象
    @RequestMapping(value = "/selectAll",method=RequestMethod.GET)
    public Object selectAllSong(HttpServletRequest request){
        return songService.selectAll();
    }

    //根据歌单名字模糊查询
    @RequestMapping(value = "/selectByTitle",method=RequestMethod.GET)
    public Object selectByName(HttpServletRequest request){
        String name=request.getParameter("name").trim();
        return songService.selectByName(name);
    }

    //更新歌曲图片
    @RequestMapping(value = "/updateSongPic",method=RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile mpFile,@RequestParam("id")int id){
        JSONObject jsonObject=new JSONObject();
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+mpFile.getOriginalFilename();

        //文件路径
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";

        //如果文件路径不存在，新增该路径
        File file1=new File(filePath);
        if(!file1.exists()){
            file1.mkdirs();
        }

        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);

        //储存到数据库里的相对文件地址
        String storeAvatorPath="/img/songPic/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song=new Song();
            song.setId(id);
            song.setPicture(storeAvatorPath);

            boolean flag=songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    //更新歌曲
    @RequestMapping(value = "/updateSongUrl",method=RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile mpFile,@RequestParam("id")int id){
        JSONObject jsonObject=new JSONObject();
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+mpFile.getOriginalFilename();

        //文件路径
        String filePath=System.getProperty("user.dir")
                +System.getProperty("file.separator")+"song";

        //如果文件路径不存在，新增该路径
        File file1=new File(filePath);
        if(!file1.exists()){
            file1.mkdirs();
        }

        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);

        //储存到数据库里的相对文件地址
        String storeAvatorPath="/song/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song=new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);

            boolean flag=songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }


    }

}
