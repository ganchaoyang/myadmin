package cn.gan.web.sys.bean.provider;

import cn.gan.web.sys.bean.SysPerm;

import java.util.Map;

public class SysPermDaoProvider {

    public String updateIgnoreNull(Map<String, Object> map){
        SysPerm perm = (SysPerm) map.get("perm");
        StringBuilder sb = new StringBuilder("update sys_perm set ");
        if (perm.getParentId() != null){
            sb.append("parent_id = #{perm.parentId}, ");
        }
        if (perm.getCode() != null){
            sb.append("code = #{perm.code}, ");
        }
        if (perm.getHref() != null){
            sb.append("href = #{perm.href}, ");
        }
        if (perm.getIcon() != null){
            sb.append("icon = #{perm.icon}, ");
        }
        if (perm.getNote() != null){
            sb.append("note = #{perm.note}, ");
        }
        if (perm.getName() != null){
            sb.append("name = #{perm.name}, ");
        }
        if (perm.getType() != null){
            sb.append("type = #{perm.type}, ");
        }
        if (perm.getOpBy() != null){
            sb.append("op_by = #{perm.opBy}, ");
        }
        if (perm.getUpdateTime() != null){
            sb.append("update_time = #{perm.updateTime}, ");
        }
        sb.append("has_children = #{perm.hasChildren}, disabled = #{perm.disabled} where id = #{perm.id}");
        return sb.toString();
    }

}
