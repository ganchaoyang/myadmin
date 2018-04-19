package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.service.SysPermService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        if (sysPerm.getParentId() != null)
            parent = sysPermService.findById(sysPerm.getParentId());
        sysPerm.setCode(SysPerm.generateCode(parent, sysPerm));
        if (sysPermService.countByCode(sysPerm.getCode()) > 0)
            return Result.error("权限标识已存在！");
        // 可以添加。
        sysPerm.setOpBy((String) session.getAttribute("me"));
        sysPermService.addPerm(sysPerm);
        return Result.success("添加成功！");
    }

}
