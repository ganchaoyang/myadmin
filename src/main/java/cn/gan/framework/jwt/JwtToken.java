package cn.gan.framework.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author ganchaoyang
 * @date 2018/10/11 11:00
 * @description
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
