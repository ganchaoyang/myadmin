package cn.gan.web.sys.service.impl;

import cn.gan.framework.util.Strings;
import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.mapper.SysRoleMapper;
import cn.gan.web.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public int addRole(SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
        sysRole.setCode(Strings.leftComplement((sysRoleMapper.countRoleNumber()+1) + "", '0', 4));
        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public int countByName(String name) {
        return sysRoleMapper.countByName(name);
    }

    @Override
    public int countByNote(String note) {
        return sysRoleMapper.countByNote(note);
    }
}
