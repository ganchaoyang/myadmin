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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/unit")
public class SysUnitController {

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUnitService sysUnitService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @RequiresUser
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

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @RequiresUser
    public Result<String> edit(@RequestBody SysUnit unit, HttpSession session){
        logger.debug("edit unit id is {}, name is {}", unit.getId(), unit.getName()==null?"":unit.getName());
        if (!sysUnitService.isExistById(unit.getId()))
            return Result.error("该单位不存在！");
        unit.setUpdateTime(new Date());
        unit.setOpBy((String) session.getAttribute("me"));
        sysUnitService.updateIgnoreNull(unit);
        return Result.success("修改成功！");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresUser
    public Result<String> delete(@PathVariable("id") String id){
        // 删除一个单位，会将其所有的子单位都删除干净。
        // 首先取出该单位。
        SysUnit unit = sysUnitService.findById(id);
        if (unit == null) return Result.error("该单位不存在！");
        // 删除该单位及其子单位。
        sysUnitService.deleteWithAllChildren(unit);
        // 如果有父级单位的话，取出其父级单位。
        if (unit.getParentId() != null && unit.getParentId().length() > 0){
            SysUnit parent = sysUnitService.findById(unit.getParentId());
            // 判断父级单位是否有其他子集单位。
            if (sysUnitService.countChildrenNumber(parent.getId()) == 0) {
                parent.setHasChildren(false);
                sysUnitService.updateIgnoreNull(parent);
            }
        }
        return Result.success("删除成功！");
    }
}
