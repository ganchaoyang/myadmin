package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysUnit;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.service.SysUnitService;
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
@RequestMapping("/unit")
public class SysUnitController {

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUnitService sysUnitService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result<List<SysUnit>> findAll(boolean tree){
        logger.debug("find all units, tree is {}", tree);
        List<SysUnit> units = sysUnitService.findAll(tree);
        return Result.success(units);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresUser
    public Result<String> add(@RequestBody SysUnit unit, HttpSession session){
        logger.debug("add unit:{}", JSON.toJSONString(unit));
        if (unit.getParentId() != null && unit.getParentId().length()>0){ // 存在父级单位。
            SysUnit parentUnit = sysUnitService.findById(unit.getParentId());
            if (parentUnit == null){
                return Result.error("父级单位不存在！");
            }
        }
        // 设置操作人。
        unit.setOpBy((String) session.getAttribute("me"));
        if (sysUnitService.addUnit(unit) == 1)
            return Result.success("添加单位成功！");
        return Result.error("未知错误");
    }

}
