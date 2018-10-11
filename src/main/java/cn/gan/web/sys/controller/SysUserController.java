package cn.gan.web.sys.controller;

import cn.gan.framework.constans.ErrorCode;
import cn.gan.framework.facade.BaseResponse;
import cn.gan.framework.jwt.JwtUtil;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.service.SysUnitService;
import cn.gan.web.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUnitService sysUnitService;

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    // 用户登录。
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<String> login(@RequestBody Map<String,Object> reqMap, HttpServletRequest request){
        System.out.println(request.getRequestURI() + ":" + request.getSession().getId());
        String username = (String) reqMap.get("username"), password = (String) reqMap.get("password");
        logger.debug("user login username : {} , password : {}", username, password);
        SysUser sysUser = sysUserService.findByLoginName(username,false);
        if (null == sysUser) {
            return BaseResponse.error(ErrorCode.LOGIN_FAILED);
        }
        if (sysUser.getPassword().equals(new Sha256Hash(password, sysUser.getSalt()).toHex())) {
            // 密码正确。
            request.getSession().setAttribute("me", sysUser.getId());
            return BaseResponse.success(JwtUtil.genJwtToken(sysUser.getLoginName(),
                    sysUser.getPassword()), "登录成功！");
        } else {
            // 密码错误。
            return BaseResponse.error(ErrorCode.LOGIN_FAILED);
        }
    }

    @RequiresUser
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResponse<SysUser> userInfo(){
        try {
            String username = JwtUtil.getUsername((String) SecurityUtils.getSubject().getPrincipal());
            SysUser user = sysUserService.findByLoginName(username, true);
            // 查询该用户的所有权限。
            user.setPerms(sysUserService.findPermsOfUser(user.getId(), false));
            return BaseResponse.success(user);
        } catch (Exception e) {
            logger.error("获取用户信息异常：{}", e);
            return BaseResponse.error(ErrorCode.SYSTEM_ERROR);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResponse<String> logout(HttpSession session){
        try {
            session.invalidate();
        }finally {
            return BaseResponse.success("退出成功!");
        }
    }

    // 查询所有用户。
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @RequiresPermissions("sys.user.view")
    public BaseResponse<List<SysUser>> data(){
        // TODO 加上分页。
        List<SysUser> users = sysUserService.findAll();
        return BaseResponse.success(users);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("sys.user.add")
    public BaseResponse<String> add(@RequestBody SysUser sysUser, HttpSession session){
        logger.debug("login user is : {}, create user : {}",session.getAttribute("me"), JSON.toJSONString(sysUser));
        if (sysUser.getUnitId() == null || !sysUnitService.isExistById(sysUser.getUnitId())) {
            return BaseResponse.error(ErrorCode.PARAMS_ERROR, "单位选择错误！");
        }
        sysUserService.addUser(sysUser);
        return BaseResponse.success("操作成功！");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("sys.user.del")
    public BaseResponse<String> delete(@PathVariable("id") String id, HttpSession session){
        logger.debug("delete user : {}", id);
        if (id.equals(session.getAttribute("me"))) {
            return BaseResponse.error(ErrorCode.ILLEGAL_OPERATION, "对不起，您无法删除自己！");
        }
        sysUserService.deleteById(id);
        return BaseResponse.success("操作成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @RequiresPermissions("sys.user.edit")
    public BaseResponse<String> edit(@RequestBody SysUser sysUser, HttpSession session){
        // 判断这个用户是否存在。
        logger.debug("update user : {}", JSON.toJSONString(sysUser));
        // 根据登录名称查询用户。
        if (sysUser.getLoginName()!=null){
            SysUser exist = sysUserService.findByLoginName(sysUser.getLoginName(), false);
            if (!exist.getId().equals(sysUser.getId())) {
                return BaseResponse.error(ErrorCode.PARAMS_ERROR, "该登录名称已存在！");
            }
        }
        sysUser.setUpdateTime(new Date());
        int res = sysUserService.updateIgnoreNull(sysUser);
        if (res == 0) {
            return BaseResponse.error(ErrorCode.SYSTEM_ERROR, "更新失败！");
        }
        return BaseResponse.success("操作成功！");
    }

}
