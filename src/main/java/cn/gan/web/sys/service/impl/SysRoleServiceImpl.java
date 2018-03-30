package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.mapper.SysRoleMapper;
import cn.gan.web.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }
}
