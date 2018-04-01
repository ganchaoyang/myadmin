package cn.gan.web.sys.service;

import cn.gan.web.sys.bean.SysUnit;

import java.util.List;

public interface SysUnitService {

    /**
     * 查询所有的单位
     * @param tree
     *          true:按照树状结构返回。
     * @return
     */
    List<SysUnit> findAll(boolean tree);

}
