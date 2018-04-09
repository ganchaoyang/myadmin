package cn.gan.web.sys.bean.provider;

import cn.gan.web.sys.bean.SysUnit;

import java.util.Map;

public class SysUnitDaoProvider {

    public String updateIgnoreNull(Map<String, Object> map){
        SysUnit unit = (SysUnit) map.get("unit");
        StringBuilder sb = new StringBuilder("update sys_unit set ");
        if (unit.getParentId() != null){
            sb.append("parent_id = #{unit.parentId}, ");
        }
        if (unit.getAddress() != null){
            sb.append("address = #{unit.address}, ");
        }
        if (unit.getAliasName() != null){
            sb.append("alias_name = #{unit.aliasName}, ");
        }
        if (unit.getEmail() != null){
            sb.append("email = #{unit.email}, ");
        }
        if (unit.getUnitCode() != null){
            sb.append("unit_code = #{unit.unitCode}, ");
        }
        if (unit.getName() != null){
            sb.append("name = #{unit.name}, ");
        }
        if (unit.getNote() != null){
            sb.append("note = #{unit.note}, ");
        }
        if (unit.getOpBy() != null){
            sb.append("op_by = #{unit.opBy}, ");
        }
        if (unit.getPath() != null){
            sb.append("path = #{unit.path}, ");
        }
        if (unit.getTelePhone() != null){
            sb.append("tele_phone = #{unit.telePhone}, ");
        }
        if (unit.getWebsite() != null){
            sb.append("website = #{unit.website}, ");
        }
        sb.append("has_children = #{unit.hasChildren} where id = #{unit.id}");
        return sb.toString();
    }

}
