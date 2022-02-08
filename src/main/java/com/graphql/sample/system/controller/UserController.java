package com.graphql.sample.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graphql.sample.framework.security.JWTUserDetails;
import com.graphql.sample.system.domain.bean.SystemUser;
import com.graphql.sample.system.domain.query.UserInfoQuery;
import com.graphql.sample.system.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationProvider authenticationProvider;
    private final SystemUserService userService;

    @MutationMapping
    @PreAuthorize("isAnonymous()")
    public JWTUserDetails login(@Argument String email, @Argument String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return userService.getCurrentUser();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Incorrect username/password");
        }
    }

    @MutationMapping
    @PreAuthorize("isAnonymous()")
    public Map<String, Object> register(@Argument("input") SystemUser systemUser) {
        return userService.registerUser(systemUser);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<SystemUser> users() {
        return userService.getAllUsers();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<SystemUser> queryUsers(@Argument("input") UserInfoQuery userInfoQuery) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userInfoQuery.getUsername())) {
            queryWrapper.lambda().eq(SystemUser::getUsername, userInfoQuery.getUsername());
        }
        return userService.list(queryWrapper);
    }
}
