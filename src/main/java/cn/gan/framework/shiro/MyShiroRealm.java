package cn.gan.framework.shiro;

import cn.gan.framework.jwt.JwtToken;
import cn.gan.framework.jwt.JwtUtil;
import cn.gan.framework.util.CollectionsUtil;
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

/**
 * @author ganchaoyang
 */
public class MyShiroRealm extends AuthorizingRealm{

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户登录名
        String loginName = JwtUtil.getUsername((String) principalCollection.getPrimaryPrincipal());
        if (null == loginName) {
            return authorizationInfo;
        }
        // 查询用户
        SysUser sysUser = sysUserService.findByLoginName(loginName, true);
        if (null == sysUser) {
            return authorizationInfo;
        }
        // 查询权限
        sysUser.setPerms(sysUserService.findPermsOfUser(sysUser.getId(), false));
        if (CollectionsUtil.isNotNullOrEmpty(sysUser.getRoles())) {
            sysUser.getRoles().forEach(one -> authorizationInfo.addRole(one.getNote()));
        }
        if (CollectionsUtil.isNotNullOrEmpty(sysUser.getPerms())) {
            sysUser.getPerms().forEach(one -> authorizationInfo.addStringPermission(one.getCode()));
        }
        return authorizationInfo;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String jwtToken = (String) authenticationToken.getCredentials();
        String username = JwtUtil.getUsername(jwtToken);
        if (null == username) {
            throw new AuthenticationException("错误的登录凭证！");
        }
        // 从数据库中获取该用户。
        SysUser sysUser = sysUserService.findByLoginName(username, true);
        if (null == sysUser) {
            throw new AuthenticationException("用户不存在！");
        }
        if (!JwtUtil.verify(jwtToken, username, sysUser.getPassword())) {
            throw new AuthenticationException("账号或密码错误！");
        }
        // 查询该用户的所有权限。
        sysUser.setPerms(sysUserService.findPermsOfUser(sysUser.getId(), false));
        SimpleAccount simpleAccount =
                new SimpleAccount(jwtToken, jwtToken, getName());
        return simpleAccount;
    }
}
