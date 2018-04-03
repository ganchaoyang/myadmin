package cn.gan.web.sys.bean.provider;

import cn.gan.web.sys.bean.SysRole;
import cn.gan.web.sys.bean.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class SysUserDaoProvider {

    private Logger logger = LoggerFactory.getLogger(SysUserDaoProvider.class);

    public String addRoles(Map<String, Object> map){
        List<SysRole> roles = (List<SysRole>) map.get("roles");
        StringBuilder sb = new StringBuilder("insert into sys_user_role(user_id, role_id) values ");
        MessageFormat mf = new MessageFormat("#'{'roles[{0}].id'}'");
        for (int i=0; i<roles.size(); i++){
            sb.append('(');
            sb.append("#{user.id}, ");
            sb.append(mf.format(new Object[]{i}));
            sb.append(')');

            if (i < (roles.size()-1))
                sb.append(",");
        }
        logger.debug("addRoles : {}", sb.toString());
        return sb.toString();
    }

    public String updateIgnoreNull(Map<String, Object> map){
        SysUser user = (SysUser) map.get("user");
        StringBuilder sb = new StringBuilder("update sys_user set ");
        if (user.getLoginName() != null){
            sb.append("login_name = #{user.loginName}, ");
        }
        if (user.getPassword() != null){
            sb.append("password = #{user.password}, ");
        }
        if (user.getSalt() != null){
            sb.append("salt = #{user.salt}, ");
        }
        if (user.getUnitId() != null){
            sb.append("unit_id = #{user.unitId}, ");
        }
        if (user.getEmail() != null){
            sb.append("email = #{user.email}, ");
        }
        if (user.getMobile() != null){
            sb.append("mobile = #{user.mobile}, ");
        }
        if (user.getNickname() != null){
            sb.append("nickname = #{user.nickname}, ");
        }if (user.getOpBy() != null){
            sb.append("op_by = #{user.opBy}, ");
        }
        if (user.getUpdateTime() != null){
            sb.append("update_time = #{user.updateTime}, ");
        }
        sb.append("is_disabled = #{user.isDisabled} where id = #{user.id}");
        return sb.toString();
    }

}
