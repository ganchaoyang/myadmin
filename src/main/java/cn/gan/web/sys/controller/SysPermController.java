package cn.gan.web.sys.controller;

import cn.gan.web.sys.bean.Result;
import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.service.SysPermService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
