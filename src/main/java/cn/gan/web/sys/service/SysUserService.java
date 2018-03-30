package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysUser;

public interface SysUserService {

    int addUser(SysUser sysUser);

    SysUser fetchByLoginName(String loginName);

}
