package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysUnit;
import cn.gan.web.sys.bean.provider.SysUnitDaoProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SysUnitMapper {

    @Select("select * from sys_unit")
    List<SysUnit> findAll();

    @Select("select * from sys_unit where id = #{id}")
    SysUnit findById(String id);

    @Select("select count(id) from sys_unit where id = #{id}")
    int count(String id);

    /**
     * 统计根单位的数目。
     * @return
     */
    @Select("select count(id) from sys_unit where parent_id is null")
    int countRootUnit();

    @Select("select count(id) from sys_unit where parent_id = #{parentId}")
    int countChildren(String parentId);

    @Insert("insert into sys_unit(id, parent_id, path, name, alias_name, " +
            "unit_code, note, address, tele_phone, email, website, has_children, create_time, update_time, op_by) values(" +
            "#{unit.id}, #{unit.parentId}, #{unit.path}, #{unit.name}, #{unit.aliasName}, #{unit.unitCode}, " +
            "#{unit.note}, #{unit.address}, #{unit.telePhone}, #{unit.email}, #{unit.website}," +
            " #{unit.hasChildren}, #{unit.createTime}, #{unit.updateTime}, #{unit.opBy})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "unit.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(@Param("unit") SysUnit unit);

    @UpdateProvider(type = SysUnitDaoProvider.class, method = "updateIgnoreNull")
    int updateIgnoreNull(@Param("unit") SysUnit unit);

}
