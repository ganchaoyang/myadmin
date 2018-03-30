package cn.gan.web.sys.service.impl;

import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.bean.mapper.SysUserMapper;
import cn.gan.web.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int addUser(SysUser sysUser) {
        return 0;
    }

    @Override
    public SysUser fetchByLoginName(String loginName) {
        return sysUserMapper.findByLoginName(loginName);
    }
}
