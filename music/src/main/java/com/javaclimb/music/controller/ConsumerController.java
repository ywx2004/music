package com.javaclimb.music.controller;

//用户controller

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Consumer;
import com.javaclimb.music.service.ConsumerService;
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
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    //添加用户
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile) {
        JSONObject jsonObject = new JSONObject();

        //获取前端传来的参数
        String username = request.getParameter("username").trim();//名
        String password = request.getParameter("password").trim();//密码
        String sex = request.getParameter("sex").trim();//性别
        String hobby = request.getParameter("hobby").trim();//爱好
        String avator = "/avatorImages/avator.jpg";//默认头像

        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "密码不能为空");
            return jsonObject;
        }

        //保存到用户对象中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Byte.valueOf(sex));
        consumer.setHobby(hobby);
        consumer.setAvator(avator);
        boolean flag = consumerService.insert(consumer);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }


    //修改用户
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id").trim();
        String username = request.getParameter("username").trim();//名
        String password = request.getParameter("password").trim();//密码
        String sex = request.getParameter("sex").trim();//性别
        String hobby = request.getParameter("hobby").trim();//爱好
        //String avator = request.getParameter("avator").trim();//头像

        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "密码不能为空");
            return jsonObject;
        }
        //保存到用户对象中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Byte.valueOf(sex));
        consumer.setHobby(hobby);

        boolean flag = consumerService.insert(consumer);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    // 删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "ID不能为空");
            return jsonObject;
        }

        try {
            int userId = Integer.parseInt(id.trim());
            boolean flag = consumerService.delete(userId);

            if (flag) {
                jsonObject.put("code", 1);
                jsonObject.put("message", "删除成功");
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("message", "删除失败");
            }
        } catch (NumberFormatException e) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "无效的ID格式");
        }

        return jsonObject;
    }

    //根据主键查询整个对象
    @RequestMapping(value = "/select",method=RequestMethod.GET)
    public Object selectSong(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        return consumerService.select(Integer.parseInt(id));
    }

    //查询所有用户
    @RequestMapping(value = "/selectAll",method=RequestMethod.GET)
    public Object selectAll(HttpServletRequest request){
        return consumerService.selectAll();
    }

    //根据用户名字模糊查询
    @RequestMapping(value = "/selectByUsername",method=RequestMethod.GET)
    public Object selectByUsername(HttpServletRequest request){
        String username=request.getParameter("username").trim();
        return consumerService.selectByUsername(username);
    }

    //更新头像
    @RequestMapping(value = "/updateConsumerPic",method=RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile mpFile,@RequestParam("id")int id){
        JSONObject jsonObject=new JSONObject();
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName=System.currentTimeMillis()+mpFile.getOriginalFilename();

        //文件路径
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImages";

        //如果文件路径不存在，新增该路径
        File file1=new File(filePath);
        if(!file1.exists()){
            file1.mkdirs();
        }

        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);

        //储存到数据库里的相对文件地址
        String storeAvatorPath="/avatorImages/"+fileName;
        try {
            mpFile.transferTo(dest);
            Consumer consumer=new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);

            boolean flag=consumerService.update(consumer);
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

    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        // 获取前端传来的参数
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        // 参数校验
        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户名不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "密码不能为空");
            return jsonObject;
        }

        // 调用服务层验证用户
        Consumer consumer = consumerService.selectByUsername(username);

        // 验证用户是否存在
        if (consumer == null) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户不存在");
            return jsonObject;
        }

        // 验证密码
        if (!consumer.getPassword().equals(password)) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "密码错误");
            return jsonObject;
        }

        // 登录成功
        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "登录成功");
        jsonObject.put("user", consumer); // 可选：返回用户信息

        return jsonObject;
    }


}


