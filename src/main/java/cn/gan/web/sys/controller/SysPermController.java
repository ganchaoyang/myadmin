package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.service.SysPermService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/perm")
public class SysPermController {

    private final Logger logger = LoggerFactory.getLogger(SysPermController.class);

    @Autowired
    private SysPermService sysPermService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result<List<SysPerm>> findAll(boolean tree){
        logger.debug("find all units, tree is {}", tree);
        List<SysPerm> perms = sysPermService.findAll(tree);
        return Result.success(perms);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresUser
    public Result<String> add(@RequestBody SysPerm sysPerm, HttpSession session){
        logger.debug("add perm, {}", JSON.toJSONString(sysPerm));
        if (sysPermService.countByName(sysPerm.getName()) > 0)
            return Result.error("权限名称已存在！");
        SysPerm parent = null;
        if (sysPerm.getParentId() != null){
            parent = sysPermService.findById(sysPerm.getParentId());
            parent.setHasChildren(true);
            sysPermService.updateIgnoreNull(parent);
        }
        sysPerm.setCode(SysPerm.generateCode(parent, sysPerm));
        if (sysPermService.countByCode(sysPerm.getCode()) > 0)
            return Result.error("权限标识已存在！");
        // 可以添加。
        sysPerm.setOpBy((String) session.getAttribute("me"));
        sysPermService.addPerm(sysPerm);
        return Result.success("添加成功！");
    }


    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @RequiresUser
    public Result<String> edit(@RequestBody SysPerm sysPerm, HttpSession session){
        logger.debug("update perm {}", JSON.toJSONString(sysPerm));
        // 先查询出旧的权限。
        SysPerm old = sysPermService.findById(sysPerm.getId());
        if (!sysPerm.getName().equals(old.getName()) &&
                sysPermService.countByName(sysPerm.getName()) > 0){
            return Result.error("权限名称已存在！");
        }
        SysPerm parent = null;
        if (sysPerm.getParentId() != null)
            parent = sysPermService.findById(sysPerm.getParentId());
        sysPerm.setCode(SysPerm.generateCode(parent, sysPerm));
        if (!sysPerm.getCode().equals(old.getCode()) &&
                sysPermService.countByCode(sysPerm.getCode()) > 0){
            return Result.error("权限标识已存在！");
        }
        // 可以修改。
        sysPerm.setOpBy((String) session.getAttribute("me"));
        sysPerm.setNote(sysPerm.getCode());
        sysPermService.updateIgnoreNull(sysPerm);
        return Result.success("修改成功！");
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresUser
    public Result<String> delete(@PathVariable("id") String id){
        logger.debug("delete perm,the id is {}", id);
        // 首先查询这个权限。
        SysPerm toBeDel = sysPermService.findById(id);
        if (toBeDel == null)
            return Result.error("该权限不存在！");
        sysPermService.deleteWithAllChildren(toBeDel);
        // 查找其父级权限。
        if (toBeDel.getParentId() != null){
            SysPerm parent = sysPermService.findById(toBeDel.getParentId());
            if (parent != null &&
                    sysPermService.countChildrenNumber(parent.getParentId()) == 0){
                parent.setHasChildren(false);
                sysPermService.updateIgnoreNull(parent);
            }
        }
        return Result.success("删除成功！");
    }

}
