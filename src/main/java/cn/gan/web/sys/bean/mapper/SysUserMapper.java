package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Select("select * from sys_user")
    List<SysUser> findAll();

    @Select("select * from sys_user where login_name = #{loginName}")
    SysUser findByLoginName(String loginName);

}
