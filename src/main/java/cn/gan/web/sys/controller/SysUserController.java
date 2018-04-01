package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<Map<String, Object>> login(@RequestBody Map<String,Object> reqMap){
        String username = (String) reqMap.get("username"), password = (String) reqMap.get("password");
        logger.debug("user login username : {} , password : {}", username, password);
        SysUser sysUser = sysUserService.findByLoginName(username);
        Map<String, Object> data = new HashMap<String, Object>();
        logger.debug("user:{}", JSON.toJSONString(sysUser));
        if (sysUser == null){
            data.put("err_msg", "用户名或密码错误");
            return Result.error(data);
        }
        try{
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username,
                    new Sha256Hash(password, sysUser.getSalt()).toHex()));
        }catch (UnknownAccountException | IncorrectCredentialsException e){
            data.put("err_msg", "用户名或密码错误");
            return Result.error(data);
        }
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<String> add(@RequestBody SysUser sysUser){
        logger.debug("create user : {}", JSON.toJSONString(sysUser));
        return Result.success("成功!");
    }

}
