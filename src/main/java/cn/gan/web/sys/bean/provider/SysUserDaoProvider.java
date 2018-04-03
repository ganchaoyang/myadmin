package cn.gan.web.sys.bean.provider;

import cn.gan.web.sys.bean.SysRole;
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

}
