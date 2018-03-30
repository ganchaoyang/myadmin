package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    // 用户登录。
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<Map<String, Object>> login(String username, String password){
        logger.debug("user login username : {} , password : {}", username, password);
        SysUser sysUser = sysUserService.findByLoginName(username);
        if (null != sysUser){
            logger.debug("select result is {}", JSON.toJSONString(sysUser));
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("token", "admin");
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        data.put("roles", roles);
        data.put("introduction", "我是超级管理员");
        data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name", "Super Admin");
        return Result.success(data);
    }

    // 查询所有用户。
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result<List<SysUser>> data(){
        // TODO 加上分页。
        List<SysUser> users = sysUserService.findAll();
        return Result.success(users);
    }

}
