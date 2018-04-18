package cn.gan.web.sys.bean.provider;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SysRoleDaoProvider {

    public String removeUsers(Map<String, Object> map){
        String roleId = (String) map.get("id");
        String userIds = (String) map.get("userIds");
        StringBuilder sb = new StringBuilder("delete from sys_user_role where user_id in ");
        sb.append(Arrays.asList(userIds.split(",")).stream().map(one -> "'"+one+"'")
                .collect(Collectors.joining("(", ",", ")")));
        return sb.toString();
    }

}
