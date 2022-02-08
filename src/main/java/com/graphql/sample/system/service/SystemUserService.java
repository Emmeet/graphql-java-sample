package com.graphql.sample.system.service;


import com.graphql.sample.framework.mp.service.ICommonBaseService;
import com.graphql.sample.framework.security.JWTUserDetails;
import com.graphql.sample.system.domain.bean.SystemUser;

import java.util.List;
import java.util.Map;

public interface SystemUserService extends ICommonBaseService<SystemUser> {

    JWTUserDetails loadUserByToken(String token);

    JWTUserDetails getCurrentUser();

    List<SystemUser> getAllUsers();

    Map<String, Object> registerUser(SystemUser systemUser);
}
