package cn.gan.framework.shiro;

import cn.gan.web.sys.bean.SysUser;
import cn.gan.web.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
        SysUser sysUser = sysUserService.findByLoginName(username);
        if (sysUser == null)
            throw new UnknownAccountException();
        SimpleAccount simpleAccount =
                new SimpleAccount(username, sysUser.getPassword(), getName());
        //将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute("USER_INFO", sysUser);
        return simpleAccount;
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        System.out.println(salt);
        System.out.println(new Sha256Hash("111111", salt));
    }
}
