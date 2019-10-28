package com.sunny.hospital.config;


import com.sunny.hospital.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/30 12:04
 * @Description: TODO 扩展access()的SpEL表达式 权限校验方法
 * @Version 1.0
 */
@Component
public class AuthService {
    @Autowired
    private PermissionService permissionService;

    /**
     * 1、 判断是否已经登录了.
     * 当未登录的时候
     * principal = anonymousUser
     * authentication= AnonymousAuthenticationToken
     * 当登录之后
     * principal = userdetails.User
     * authentication = UsernamePasswordAuthenticationToken
     *
     *
     * @param request
     * @param authentication [AnonymousAuthenticationToken|UsernamePasswordAuthenticationToken]
     * @return
     */
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {

        System.out.println("AuthService.canAccess()");
        boolean b = false;
        System.out.println(authentication);

        //1/ 判断是否已经登录了，anonymousUser|userdetails.User
        Object principal = authentication.getPrincipal();
        if(principal == null || "anonymousUser".equals(principal))

        //这里可以单独把AnonymousAuthenticationToken拿出来校验，也可以将放到roles统一校验，其role为ROLE_ANONYMOUS
        if(authentication instanceof AnonymousAuthenticationToken){
            //check if this uri can be access by anonymous
            //return
        }

        //取出权限列表
        Map<String, Collection<ConfigAttribute>> map =  permissionService.getPermissionMap();

        //String uri = request.getRequestURI();
        //这种获取方式不好，不支持/User/**的匹配方式。
        //Collection<ConfigAttribute> configAttributes = map.get(uri);

        Collection<ConfigAttribute> configAttributes = null;
        String resUrl;
        //URL规则匹配.
        AntPathRequestMatcher matcher;
        for(Iterator<String> it  = map.keySet().iterator();it.hasNext();) {
            resUrl = it.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                configAttributes =  map.get(resUrl);
                break;
            }
        }

        if(configAttributes == null || configAttributes.size() ==0) {
            return b;
        }

        ConfigAttribute cfa  = null;
        String needRole = null;
        for(Iterator<ConfigAttribute> it = configAttributes.iterator(); it.hasNext();) {
            cfa = it.next();
            needRole = cfa.getAttribute();
            for(GrantedAuthority grantedAuthority:authentication.getAuthorities()) {
                if(needRole.equals(grantedAuthority.getAuthority())) {
                    System.out.println("needRole = "+needRole);
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

}
