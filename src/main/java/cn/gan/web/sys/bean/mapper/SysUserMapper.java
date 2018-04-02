package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.bean.provider.SysUserDaoProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Select("select * from sys_user")
    List<SysUser> findAll();

    @Select("select * from sys_user where login_name = #{loginName}")
    SysUser findByLoginName(String loginName);

    @Select("select * from sys_user where id = #{id}")
    SysUser findById(String id);

    @Insert("insert into sys_user(id, login_name, nickname, password, salt, " +
            "email, mobile, is_disabled, unit_id) values(" +
            "#{user.id}, #{user.loginName}, #{user.nickname}, #{user.password}, #{user.salt}, " +
            "#{user.email}, #{user.mobile}, #{user.isDisabled}, #{user.unitId})")
    @SelectKey(statement = "select replace(UUID(), '-', '') as id", keyProperty = "user.id",
            before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int insert(@Param("user") SysUser sysUser);

    @Delete("delete from sys_user where id = #{id}")
    int delete(String id);

    @InsertProvider(type = SysUserDaoProvider.class, method = "addRoles")
    int addRoles(@Param("user") SysUser sysUser, @Param("roles") List<SysRole> roles);
}
