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
    SysUser findByLoginName(String loginName, boolean link);

    /**
     * 查找所有用户。
     * @return
     */
    List<SysUser> findAll();

    /**
     * 根据Id删除用户。
     * @param id
     */
    void deleteById(String id);

    /**
     * 更新用户，忽略空值。
     * @param sysUser
     * @return
     */
    int updateIgnoreNull(SysUser sysUser);

}
