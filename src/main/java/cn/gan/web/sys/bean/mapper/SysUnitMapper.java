package cn.gan.web.sys.bean.mapper;

import cn.gan.web.sys.bean.SysUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUnitMapper {

    @Select("select * from sys_unit")
    List<SysUnit> findAll();

}
