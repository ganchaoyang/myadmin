package cn.gan.framework.shiro;

import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.service.SysUserService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class MyShiroRealm extends AuthorizingRealm{

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // TODO 需要获取用户权限。
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 获取用户输入的账号。
        String username = (String) authenticationToken.getPrincipal();
        // 从数据库中获取该用户。
        SysUser sysUser = sysUserService.findByLoginName(username, true);
        // 查询该用户的所有权限。
        sysUser.setPerms(sysUserService.findPermsOfUser(sysUser.getId(), false));
        System.out.println(JSON.toJSONString(sysUser));
        // 在实现角色模块之前，此处先这么处理。
        if (sysUser == null)
            throw new UnknownAccountException();
        SimpleAccount simpleAccount =
                new SimpleAccount(sysUser, sysUser.getPassword(), getName());
        return simpleAccount;
    }
}
