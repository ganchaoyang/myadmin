package cn.gan.framework.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomShiroFilter extends AuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {
        if (SecurityUtils.getSubject().getPrincipal() == null)
            return true;
        return false;
    }
}
