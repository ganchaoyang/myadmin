package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.bean.mapper.SysPermMapper;
import cn.gan.web.sys.service.SysPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public SysPerm findById(String id) {
        return sysPermMapper.findById(id);
    }

    @Override
    public int countByName(String name) {
        return sysPermMapper.countByName(name);
    }

    @Override
    public int countByCode(String code) {
        return sysPermMapper.countByCode(code);
    }

    @Override
    public int addPerm(SysPerm perm) {
        perm.setNote(perm.getCode());
        perm.setCreateTime(new Date());
        perm.setUpdateTime(new Date());
        return sysPermMapper.insert(perm);
    }

    @Override
    public int updateIgnoreNull(SysPerm perm) {
        perm.setUpdateTime(new Date());
        return sysPermMapper.updateIgnoreNull(perm);
    }
}
