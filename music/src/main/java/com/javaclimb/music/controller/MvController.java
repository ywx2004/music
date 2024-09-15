package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Mv;
import com.javaclimb.music.service.MvService;
import com.javaclimb.music.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/mv")
public class MvController {
    @Autowired
    private MvService mvService;

    // 添加MV
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addMv(HttpServletRequest request, @RequestParam("file") MultipartFile mpFile) {
        JSONObject jsonObject = new JSONObject();

        // 获取前端传来的参数
        String name = request.getParameter("name").trim(); // MV 名字
        String introduction = request.getParameter("introduction").trim(); // 简介
        String picture = "/img/mvPic/default.jpg"; // 默认图片
        String url = "/mv/default.mp4"; // 默认视频地址

        // 上传MV文件
        if (mpFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "MV上传失败");
            return jsonObject;
        }

        // 文件名 = 当前时间到毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + mpFile.getOriginalFilename();

        // 文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "mv";

        // 如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        // 实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);

        // 储存到数据库里的相对文件地址
        String storeUrlPath = "/mv/" + fileName;
        try {
            mpFile.transferTo(dest);
            Mv mv = new Mv();
            mv.setName(name);
            mv.setIntroduction(introduction);
            mv.setPicture(picture);
            mv.setUrl(storeUrlPath);
            boolean flag = mvService.insert(mv);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "保存成功");
                jsonObject.put("url", storeUrlPath);
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败" + e.getMessage());
        } finally {
            return jsonObject;
        }
    }

    // 修改MV
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateMv(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();

        // 保存到MV的对象中
        Mv mv = new Mv();
        mv.setId(Integer.parseInt(id));
        mv.setName(name);
        mv.setIntroduction(introduction);
        boolean flag = mvService.update(mv);
        if (flag) {
            // 保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        // 保存失败
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除MV
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteMv(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        boolean flag = mvService.delete(Integer.parseInt(id));

        if (flag) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除成功");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除失败");
        }

        return jsonObject;
    }

    // 根据主键查询整个对象
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public Object selectMv(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        return mvService.select(Integer.parseInt(id));
    }

    // 查询所有对象
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public Object selectAllMv(HttpServletRequest request) {
        return mvService.selectAll();
    }

    // 根据MV名字模糊查询
    @RequestMapping(value = "/selectByName", method = RequestMethod.GET)
    public Object selectByName(HttpServletRequest request) {
        String name = request.getParameter("name").trim();
        return mvService.selectByName(name);
    }

    // 更新MV图片
    @RequestMapping(value = "/updateMvPic", method = RequestMethod.POST)
    public Object updateMvPic(@RequestParam("file") MultipartFile mpFile, @RequestParam("id") int id) {
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
                + System.getProperty("file.separator") + "mvPic";

        // 如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        // 实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);

        // 储存到数据库里的相对文件地址
        String storeAvatorPath = "/img/mvPic/" + fileName;
        try {
            mpFile.transferTo(dest);
            Mv mv = new Mv();
            mv.setId(id);
            mv.setPicture(storeAvatorPath);

            boolean flag = mvService.update(mv);
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
            jsonObject.put(Consts.MSG, "上传失败" + e.getMessage());
        } finally {
            return jsonObject;
        }
    }

    // 更新MV视频
    @RequestMapping(value = "/updateMvUrl", method = RequestMethod.POST)
    public Object updateMvUrl(@RequestParam("file") MultipartFile mpFile, @RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();
        if (mpFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        // 文件名 = 当前时间到毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + mpFile.getOriginalFilename();

        // 文件路径
        String filePath = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "mv";

        // 如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        // 实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);

        // 储存到数据库里的相对文件地址
        String storeAvatorPath = "/mv/" + fileName;
        try {
            mpFile.transferTo(dest);
            Mv mv = new Mv();
            mv.setId(id);
            mv.setUrl(storeAvatorPath);

            boolean flag = mvService.update(mv);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("url", storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败" + e.getMessage());
        } finally {
            return jsonObject;
        }
    }
}

