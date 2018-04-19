package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysPerm;

import java.util.List;

public interface SysPermService {

    /**
     * 查询所有的权限。
     * @param tree
     *          是否以树状结构返回。
     * @return
     */
    List<SysPerm> findAll(boolean tree);

    /**
     * 通过权限Id查询。
     * @param id
     * @return
     */
    SysPerm findById(String id);

    /**
     * 根据权限名称统计。
     * @param name
     * @return
     */
    int countByName(String name);


    /**
     * 根据权限标识统计。
     * @param code
     * @return
     */
    int countByCode(String code);


    /**
     * 添加权限。
     * @param perm
     * @return
     */
    int addPerm(SysPerm perm);

}
