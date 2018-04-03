package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    @Select("select * from sys_role")
    List<SysRole> findAll();

    @Select("select * from sys_role where id in (select role_id from sys_user_role where user_id = #{id})")
    List<SysRole> findByUserId(String id);

}
