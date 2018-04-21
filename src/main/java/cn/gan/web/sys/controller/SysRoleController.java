package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @RequiresUser
    public Result<List<SysRole>> data(){
        List<SysRole> roles = sysRoleService.findAll();
        return Result.success(roles);
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @RequiresUser
    public Result<SysRole> data(@PathVariable("id") String id){
        SysRole role = sysRoleService.findById(id, true);
        return Result.success(role);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresUser
    public Result<String> add(@RequestBody SysRole sysRole, HttpSession session){
        sysRole.setOpBy((String) session.getAttribute("me"));
        // 判断这个角色的名称是否存在。
        if (sysRoleService.countByName(sysRole.getName()) > 0){
            return Result.error("该名称已存在！");
        }
        if (sysRoleService.countByNote(sysRole.getNote()) > 0){
            return Result.error("该角色标识已存在！");
        }
        sysRoleService.addRole(sysRole);
        return Result.success("添加角色成功！");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresUser
    public Result<String> delete(@PathVariable("id")String id){
        // 首先删除该角色与用户的关联关系。
        sysRoleService.clearUsers(id);
        // 删除角色。
        sysRoleService.deleteById(id);
        return Result.success("删除成功！");
    }


    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @RequiresUser
    public Result<String> edit(@RequestBody SysRole sysRole){
        // 首先判断该角色是否存在。
        SysRole toBeUpdate = sysRoleService.findById(sysRole.getId(), false);
        if (toBeUpdate == null)
            return Result.error("该角色不存在！");
        if (sysRole.getName() != null && !sysRole.getName().equals(toBeUpdate.getName()) &&
                sysRoleService.countByName(sysRole.getName()) > 0){
            return Result.error("该名称已存在！");
        }
        if (sysRole.getNote() != null && !sysRole.getNote().equals(toBeUpdate.getNote()) &&
                sysRoleService.countByNote(sysRole.getNote()) > 0){
            return Result.error("该角色标识已存在！");
        }
        sysRoleService.updateRole(sysRole);
        return  Result.success("更新成功！");
    }

}
