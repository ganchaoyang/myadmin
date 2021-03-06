package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysPerm;
import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.bean.provider.SysRoleDaoProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    @Select("select * from sys_role")
    List<SysRole> findAll();

    @Select("select * from sys_role where id in (select role_id from sys_user_role where user_id = #{id})")
    @ResultMap(value = "link")
    List<SysRole> findByUserId(String id);

    @Select("select * from sys_role where id in (select role_id from sys_role_perm where perm_id = #{id})")
    List<SysRole> findByPermId(String id);

    @Select("select * from sys_role where id = #{id}")
    SysRole findById(String id);

    @Select("select * from sys_role where id = #{id}")
    @Results(id = "link", value = {
            @Result(column = "id", property = "id"),
            @Result(property = "users", column = "id", javaType = List.class,
            many = @Many(fetchType = FetchType.EAGER, select = "cn.gan.web.sys.bean.mapper.SysUserMapper.findByRoleId")),
            @Result(property = "perms", column = "id", javaType = List.class,
            many = @Many(fetchType = FetchType.EAGER, select = "cn.gan.web.sys.bean.mapper.SysPermMapper.findByRoleId"))
    })
    SysRole findWithLinkById(String id);

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

    @Delete("delete from sys_role where id = #{id}")
    int delete(String id);

    /**
     * 根据用户ids批量移除。
     * @param id
     *          角色的id。
     * @param userIds
     *         需要移除的用户的id，以`,`号分割
     * @return
     */
    @DeleteProvider(type = SysRoleDaoProvider.class, method = "removeUsers")
    int removeUsers(String id, String userIds);

    /**
     * 移除一个角色下所有的用户。
     * @param id
     *         角色Id
     * @return
     */
    @Delete("delete from sys_user_role where role_id = #{id}")
    int clearUsers(String id);


    @Delete("delete from sys_role_perm where role_id = #{id}")
    int clearPerms(String id);


    @UpdateProvider(type = SysRoleDaoProvider.class, method = "updateIgnoreNull")
    int updateIgnoreNull(@Param("role") SysRole sysRole);


    @InsertProvider(type = SysRoleDaoProvider.class, method = "addUsers")
    int addUsers(@Param("roleId") String roleId, @Param("users") List<SysUser> users);

    @InsertProvider(type = SysRoleDaoProvider.class, method = "addPerms")
    int addPerms(@Param("id") String roleId, @Param("perms")List<SysPerm> perms);

}
