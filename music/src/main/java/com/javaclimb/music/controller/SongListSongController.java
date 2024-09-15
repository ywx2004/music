package com.javaclimb.music.controller;

//歌单歌曲管理controller

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.SongListSong;
import com.javaclimb.music.service.SongListSongService;
import com.javaclimb.music.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songListSong")
public class SongListSongController {
    @Autowired
    private SongListSongService songListSongService;

    //添加歌曲
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSongListSong(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();

        //获取前端传来的参数
        String songId=request.getParameter("songId").trim();//歌曲id
        String songListId=request.getParameter("songListId").trim();//歌单id

        SongListSong songListSong=new SongListSong();
        songListSong.setSongId(Integer.valueOf(songId));
        songListSong.setSongListId(Integer.valueOf(songListId));

        boolean flag=songListSongService.insert(songListSong);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"保存失败");
        return jsonObject;

    }

    // 修改歌曲
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        try {
            // 获取前端传来的参数
            String id = request.getParameter("id").trim();
            String songId = request.getParameter("songId").trim(); // 歌曲id
            String songListId = request.getParameter("songListId").trim(); // 歌单id

            // 保存到歌曲的对象中
            SongListSong songListSong = new SongListSong();
            songListSong.setId(Integer.parseInt(id));
            songListSong.setSongId(Integer.valueOf(songId));
            songListSong.setSongListId(Integer.valueOf(songListId));

            // 调用服务层方法进行更新
            boolean flag = songListSongService.update(songListSong);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "修改成功");
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "修改失败: " + e.getMessage());
            return jsonObject;
        }

        // 修改失败
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }


    //删除歌单里的歌曲

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<SongController.DeleteResponse> delete(HttpServletRequest request) {
        String songId=request.getParameter("songId").trim();
        String songListId=request.getParameter("songListId").trim();
        boolean success = songListSongService.deleteBySongId(Integer.parseInt(songId),Integer.parseInt(songListId));


        // 创建响应对象
        SongController.DeleteResponse response = new SongController.DeleteResponse();
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
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ResponseEntity<Object> select(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("ID parameter is required.");
        }

        try {
            Integer id = Integer.parseInt(idStr.trim());
            SongListSong songListSong = songListSongService.select(id);
            if (songListSong != null) {
                return ResponseEntity.ok(songListSong);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No song found for the given ID.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format.");
        }
    }


    //查询所有对象
    @RequestMapping(value = "/selectAll",method=RequestMethod.GET)
    public Object selectAllSong(HttpServletRequest request){
        return songListSongService.selectAll();
    }

    @RequestMapping(value = "/selectBySongListId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectBySongListId(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String songListId = request.getParameter("songListId");

        if (songListId == null || songListId.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "参数 songListId 不可为空");
            return response;
        }

        try {
            Integer id = Integer.parseInt(songListId.trim());
            List<SongListSong> result = songListSongService.selectBySongListId(id);

            response.put("status", "success");
            response.put("data", result);
        } catch (NumberFormatException e) {
            response.put("status", "error");
            response.put("message", "无效的参数 songListId");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "查询失败，请稍后再试");
        }

        return response;
    }





}
