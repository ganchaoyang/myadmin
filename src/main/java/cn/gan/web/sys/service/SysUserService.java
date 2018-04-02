package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysUser;

import java.util.List;

public interface SysUserService {

    void addUser(SysUser sysUser);

    /**
     * 根据登录名称查找用户。
     * @param loginName
     *              登录名。
     * @return
     */
    SysUser findByLoginName(String loginName);

    /**
     * 查找所有用户。
     * @return
     */
    List<SysUser> findAll();

}
