package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysPerm;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SysPermMapper {

    @Select("select * from sys_perm order by name desc")
    List<SysPerm> findAll();

    @Select("select * from sys_perm where id = #{id}")
    SysPerm findById(String id);

    @Select("select count(id) from sys_perm where name = #{name}")
    int countByName(String name);

    @Select("select count(id) from sys_perm where code = #{code}")
    int countByCode(String name);

    @Insert("insert into sys_perm(id, parent_id, code, name, note, " +
            "disabled, create_time, update_time, op_by, has_children, href, type, icon) values(" +
            "#{perm.id}, #{perm.parentId}, #{perm.code}, #{perm.name}, #{perm.note}, #{perm.disabled}, " +
            "#{perm.createTime}, #{perm.updateTime}, #{perm.opBy}, #{perm.hasChildren}, #{perm.href}," +
            " #{perm.type}, #{perm.icon})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "perm.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(@Param("perm") SysPerm perm);

}
