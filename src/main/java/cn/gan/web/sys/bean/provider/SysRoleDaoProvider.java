package cn.gan.web.sys.bean.provider;

import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.SysUser;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SysRoleDaoProvider {

    public String removeUsers(Map<String, Object> map){
        String userIds = (String) map.get("userIds");
        StringBuilder sb = new StringBuilder("delete from sys_user_role where user_id in ");
        sb.append(Arrays.asList(userIds.split(",")).stream().map(one -> "'"+one+"'")
                .collect(Collectors.joining("(", ",", ")")));
        return sb.toString();
    }

    public String addUsers(Map<String, Object> map){
        List<SysUser> users = (List<SysUser>) map.get("users");
        StringBuilder sb = new StringBuilder("insert into sys_user_role(user_id, role_id) values ");
        MessageFormat mf = new MessageFormat("#'{'users[{0}].id'}'");
        for (int i=0; i<users.size(); i++){
            sb.append('(');
            sb.append(mf.format(new Object[]{i}));
            sb.append(", #{roleId}");
            sb.append(')');
            if (i < (users.size()-1))
                sb.append(",");
        }
        return sb.toString();
    }

    public String updateIgnoreNull(Map<String, Object> map){
        SysRole role = (SysRole) map.get("role");
        StringBuilder sb = new StringBuilder("update sys_role set ");
        if (role.getName() != null){
            sb.append("name = #{role.name}, ");
        }
        if (role.getNote() != null){
            sb.append("name = #{role.note}, ");
        }
        if (role.getAliasName() != null){
            sb.append("alias_name = #{role.aliasName}, ");
        }
        if (role.getUpdateTime() != null){
            sb.append("update_time = #{role.updateTime}, ");
        }
        if (role.getOpBy() != null){
            sb.append("op_by = #{role.opBy}, ");
        }
        sb.append("disabled = #{role.disabled} where id = #{role.id}");
        return sb.toString();
    }

}
