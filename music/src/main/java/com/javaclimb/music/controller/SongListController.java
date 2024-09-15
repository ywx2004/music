package com.javaclimb.music.controller;


//歌单controller

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.SongList;
import com.javaclimb.music.service.SongListService;
import com.javaclimb.music.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    // 添加歌单
    @PostMapping("/add")
    public Object addSongList(@RequestParam("title") String title,
                              @RequestParam("picture") String picture,
                              @RequestParam("introduction") String introduction) {
        JSONObject jsonObject = new JSONObject();

        // 处理空值
        title = title != null ? title.trim() : "";
        picture = picture != null ? picture.trim() : "";
        introduction = introduction != null ? introduction.trim() : "";

        // 保存歌单到对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPicture(picture);
        songList.setIntroduction(introduction);
        boolean flag = songListService.insert(songList);

        if (flag) {
            // 保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        // 保存失败
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }


    //修改歌单
    @PostMapping("/update")
    public Object updateSong(@RequestParam(required = false) String id,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String introduction) {
        JSONObject jsonObject = new JSONObject();

        if (id == null || title == null || introduction == null) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "请求参数不完整");
            return jsonObject;
        }

        // Trim whitespace and set default values if needed
        int songListId;
        try {
            songListId = Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "无效的歌单ID");
            return jsonObject;
        }

        title = title.trim();
        introduction = introduction.trim();

        //保存到歌曲的对象中
        SongList songList = new SongList();
        songList.setId(songListId);
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        boolean flag = songListService.update(songList);
        if (flag) {
            //保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        //保存失败
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    //删除歌单
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<SongController.DeleteResponse> deleteSong(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        boolean success = songListService.delete(Integer.parseInt(id));

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
    @GetMapping("/select")
    public Object selectSongList(@RequestParam String id) {
        int songListId;
        try {
            songListId = Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            return null;
        }
        return songListService.select(songListId);
    }

    //查询所有对象
    @RequestMapping(value = "/selectAll",method=RequestMethod.GET)
    public Object selectAllSong(HttpServletRequest request){
        return songListService.selectAll();
    }

    //根据歌单名字模糊查询
    @GetMapping("/selectByTitle")
    public Object selectByTitle(@RequestParam String title) {
        String trimmedTitle = title.trim();
        return songListService.selectByTitle(trimmedTitle);
    }

    // 更新歌曲图片
    @PostMapping("/updateSongListPic")
    public Object updateSongPic(@RequestParam("file") MultipartFile mpFile, @RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();

        if (mpFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }

        // 文件名 = 当前时间到毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + mpFile.getOriginalFilename();

        // 文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "songListPic";

        // 如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        // 实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);

        // 储存到数据库里的相对文件地址
        String storeAvatorPath = "/img/songListPic/" + fileName;
        try {
            mpFile.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPicture(storeAvatorPath);

            boolean flag = songListService.update(songList);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("pic", storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败: " + e.getMessage());
        } finally {
            return jsonObject;
        }
    }

}
