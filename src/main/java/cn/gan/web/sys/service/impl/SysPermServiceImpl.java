package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.bean.mapper.SysPermMapper;
import cn.gan.web.sys.service.SysPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysPermService")
public class SysPermServiceImpl implements SysPermService{

    @Autowired
    private SysPermMapper sysPermMapper;

    @Override
    public List<SysPerm> findAll(boolean tree) {
        List<SysPerm> perms = sysPermMapper.findAll();
        if (tree) {
            perms = SysPerm.toTrees(perms);
        }
        return perms;
    }
}
