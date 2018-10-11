package cn.gan.web.sys.controller;

import cn.gan.framework.constans.ErrorCode;
import cn.gan.framework.facade.BaseResponse;
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
    public BaseResponse<List<SysRole>> data(){
        List<SysRole> roles = sysRoleService.findAll();
        return BaseResponse.success(roles);
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @RequiresUser
    public BaseResponse<SysRole> data(@PathVariable("id") String id){
        SysRole role = sysRoleService.findById(id, true, false);
        return BaseResponse.success(role);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresUser
    public BaseResponse<String> add(@RequestBody SysRole sysRole, HttpSession session){
        sysRole.setOpBy((String) session.getAttribute("me"));
        // 判断这个角色的名称是否存在。
        if (sysRoleService.countByName(sysRole.getName()) > 0){
            return BaseResponse.error(ErrorCode.PARAMS_ERROR, "该名称已存在！");
        }
        if (sysRoleService.countByNote(sysRole.getNote()) > 0){
            return BaseResponse.error(ErrorCode.PARAMS_ERROR, "该角色标识已存在！");
        }
        sysRoleService.addRole(sysRole);
        return BaseResponse.success("添加角色成功！");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresUser
    public BaseResponse<String> delete(@PathVariable("id")String id){
        // 删除角色。
        sysRoleService.deleteById(id);
        return BaseResponse.success("删除成功！");
    }


    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @RequiresUser
    public BaseResponse<String> edit(@RequestBody SysRole sysRole){
        // 首先判断该角色是否存在。
        SysRole toBeUpdate = sysRoleService.findById(sysRole.getId(), false, false);
        if (toBeUpdate == null){
            return BaseResponse.error(ErrorCode.PARAMS_ERROR, "该角色不存在！");
        }
        if (sysRole.getName() != null && !sysRole.getName().equals(toBeUpdate.getName()) &&
                sysRoleService.countByName(sysRole.getName()) > 0){
            return BaseResponse.error(ErrorCode.PARAMS_ERROR, "该名称已存在！");
        }
        if (sysRole.getNote() != null && !sysRole.getNote().equals(toBeUpdate.getNote()) &&
                sysRoleService.countByNote(sysRole.getNote()) > 0){
            return BaseResponse.error(ErrorCode.PARAMS_ERROR, "该角色标识已存在！");
        }
        sysRoleService.updateRole(sysRole);
        return  BaseResponse.success("更新成功！");
    }

}
