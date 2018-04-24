package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.bean.provider.SysPermDaoProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SysPermMapper {

    @Select("select * from sys_perm order by name desc")
    @Results(id = "link", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(fetchType = FetchType.EAGER, select = "cn.gan.web.sys.bean.mapper.SysRoleMapper.findByPermId"))
    })
    List<SysPerm> findAll();

    @Select("select * from sys_perm where id = #{id}")
    SysPerm findById(String id);

    @Select("select * from sys_perm where id in (select perm_id from sys_role_perm where role_id = #{roleId})")
    List<SysPerm> findByRoleId(String roleId);

    @Select("select count(id) from sys_perm where name = #{name}")
    int countByName(String name);

    @Select("select count(id) from sys_perm where code = #{code}")
    int countByCode(String name);

    @Select("select count(id) from sys_perm where parent_id = #{parentId}")
    int countChildren(String parentId);

    @Insert("insert into sys_perm(id, parent_id, code, name, note, " +
            "disabled, create_time, update_time, op_by, has_children, href, type, icon) values(" +
            "#{perm.id}, #{perm.parentId}, #{perm.code}, #{perm.name}, #{perm.note}, #{perm.disabled}, " +
            "#{perm.createTime}, #{perm.updateTime}, #{perm.opBy}, #{perm.hasChildren}, #{perm.href}," +
            " #{perm.type}, #{perm.icon})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "perm.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(@Param("perm") SysPerm perm);

    @UpdateProvider(type = SysPermDaoProvider.class, method = "updateIgnoreNull")
    int updateIgnoreNull(@Param("perm") SysPerm perm);

    @Delete("delete from sys_perm where code like #{rex}")
    int deleteLikeWithCode(String rex);

}
