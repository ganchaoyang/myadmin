package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    @Select("select * from sys_role")
    List<SysRole> findAll();

    @Select("select * from sys_role where id in (select role_id from sys_user_role where user_id = #{id})")
    List<SysRole> findByUserId(String id);

    @Select("select count(id) from sys_role")
    int countRoleNumber();

    @Select("select count(id) from sys_role where name = #{name}")
    int countByName(String name);

    @Select("select count(id) from sys_role where note = #{note}")
    int countByNote(String note);

    @Insert("insert into sys_role(id, name, code, alias_name, disabled, " +
            "note, create_time, update_time, op_by) values(" +
            "#{role.id}, #{role.name}, #{role.code}, #{role.aliasName}, #{role.disabled}, " +
            "#{role.note}, #{role.createTime}, #{role.updateTime}, #{role.opBy})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "role.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(@Param("role") SysRole role);

}
