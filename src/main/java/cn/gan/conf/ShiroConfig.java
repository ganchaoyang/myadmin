package cn.gan.conf;

import cn.gan.framework.filter.CustomShiroFilter;
import cn.gan.framework.shiro.MyShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        logger.debug("init shiro filter...");
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String , Filter> customFilters = new LinkedHashMap<>();
        customFilters.put("authc", new CustomShiroFilter());
        shiroFilter.setFilters(customFilters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilter.setLoginUrl("/user/login");
        return shiroFilter;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm());
        return defaultWebSecurityManager;
    }

}
