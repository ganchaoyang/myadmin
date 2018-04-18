package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermMapper {

    @Select("select * from sys_perm order by name desc")
    List<SysPerm> findAll();

}
