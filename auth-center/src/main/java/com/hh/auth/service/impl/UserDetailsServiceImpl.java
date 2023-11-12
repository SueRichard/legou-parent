package com.hh.auth.service.impl;

import com.hh.auth.client.UserClient;
import com.hh.legou.security.po.Role;
import com.hh.legou.security.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 按照Spring Security规范自定义UserDetailsService
 *
 * @author hh
 * @version 1.0
 * @time 12/11/2023 14:33
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserClient userClient;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过feign调用用户微服务，这里返回的是自己定义的user对象
        User user = userClient.getByUserName(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user != null) {
            log.debug("current user = {}", user);
            List<Role> roles = userClient.selectRolesByUserId(user.getId());
            for (Role role : roles) {
                if (role != null && role.getName() != null) {
                    //spring security 要求权限名称要有前缀"ROLE_"，即"ROLE_ADMIN"
                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
        }
        //最后返回的是spring security内置的对象，security对象实现了UserDetails
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}
