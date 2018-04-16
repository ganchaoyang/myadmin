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


    /**
     * 根据id查询单位。
     * @param id
     * @return
     */
    SysUnit findById(String id);


    /**
     * 根据父单位和名称查找单位
     * @param parentId
     * @param name
     * @return
     */
    SysUnit findByPidAndName(String parentId, String name);


    /**
     * 根据id该记录是否存在。
     * @param id
     * @return
     */
    boolean isExistById(String id);


    /**
     * 添加单位。
     * @param unit
     * @return
     */
    int addUnit(SysUnit unit);

    /**
     * 更新一个单位。
     * @param unit
     * @return
     */
    int updateIgnoreNull(SysUnit unit);


    /**
     * 删除一个单位，并随即删除其所有子单位。
     * @param unit
     * @return
     */
    int deleteWithAllChildren(SysUnit unit);


    /**
     * 统计一个单位有多少个子单位。
     * @param id
     * @return
     */
    int countChildrenNumber(String id);
}
