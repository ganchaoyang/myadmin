package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysRole;

import java.util.List;

public interface SysRoleService {


    /**
     * 查询所有的角色。
     * @return
     */
    List<SysRole> findAll();

}
