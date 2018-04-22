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
     * 通过Id查询。
     * @param id
     * @param link
     *      是否需要携带关联字段。
     * @param permsTree
     *      角色权限是否需要以树状结构返回。
     * @return
     */
    SysRole findById(String id, boolean link, boolean permsTree);

    /**
     * 新增一个角色
     * @param sysRole
     * @return
     */
    int addRole(SysRole sysRole);

    /**
     * 根据Id删除角色。
     * @param id
     * @return
     */
    int deleteById(String id);

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


    /**
     * 移除一个角色关联的所有用户的关联关系。
     * @param id
     *     角色Id.
     * @return
     */
    int clearUsers(String id);


    /**
     * 更新一个角色。
     * @param sysRole
     * @return
     */
    int updateRole(SysRole sysRole);

}
