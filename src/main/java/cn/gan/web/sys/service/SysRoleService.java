package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysRole;

import java.util.List;

public interface SysRoleService {


    /**
     * 查询所有的角色。
     * @return
     */
    List<SysRole> findAll();


    /**
     * 新增一个角色
     * @param sysRole
     * @return
     */
    int addRole(SysRole sysRole);

    /**
     * 根据角色名称统计角色数量。
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * 根据角色标识统计角色数量。
     * @param note
     * @return
     */
    int countByNote(String note);

}
