package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysUnit;
import cn.gan.web.sys.service.SysUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
