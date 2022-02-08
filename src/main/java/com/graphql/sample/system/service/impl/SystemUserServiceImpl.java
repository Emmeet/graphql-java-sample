package com.graphql.sample.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.graphql.sample.framework.exception.BadTokenException;
import com.graphql.sample.framework.mp.service.impl.ICommonBaseServiceImpl;
import com.graphql.sample.framework.security.JWTUserDetails;
import com.graphql.sample.framework.security.SecurityProperties;
import com.graphql.sample.framework.utils.RedisUtils;
import com.graphql.sample.system.domain.bean.SystemUser;
import com.graphql.sample.system.domain.bo.SystemUserBo;
import com.graphql.sample.system.mapper.SystemUserMapper;
import com.graphql.sample.system.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl extends ICommonBaseServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService, UserDetailsService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final RedisUtils redisUtils;
    private final SecurityProperties securityProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUserBo user = baseMapper.querySystemUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Incorrect username/password"));
        JWTUserDetails details = getUserDetails(user, getToken(user));
        redisUtils.set("user_" + details.getUsername(), details.getToken(), securityProperties.getTokenExpiration().getSeconds());
        redisUtils.set(details.getToken(), JSONUtil.toJsonStr(details), securityProperties.getTokenExpiration().getSeconds());
        return details;
    }

    @Override
    public JWTUserDetails loadUserByToken(String token) {
        JWTUserDetails jwtUserDetails = Optional.ofNullable(redisUtils.get(token))
                .map(json -> JSONUtil.toBean(json.toString(), JWTUserDetails.class))
                .orElseThrow(BadTokenException::new);
        String subject = getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .orElseThrow(BadTokenException::new);
        if (!jwtUserDetails.getUsername().equals(subject)) {
            throw new BadTokenException();
        }
        if (jwtUserDetails != null) {
            jwtUserDetails.setAuthorities(jwtUserDetails.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));
        }
        return jwtUserDetails;
    }

    @Override
    public JWTUserDetails getCurrentUser() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .map(name -> redisUtils.get("user_" + name).toString())
                .map(key -> redisUtils.get(key))
                .map(json -> JSONUtil.toBean(json.toString(), JWTUserDetails.class))
                .orElse(null);
    }

    @Override
    public List<SystemUser> getAllUsers() {
        return list();
    }

    @Override
    public Map<String, Object> registerUser(SystemUser systemUser) {
        systemUser.setPassword(new BCryptPasswordEncoder().encode(systemUser.getPassword()));
        Boolean flag = save(systemUser);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("user", systemUser);
        return map;
    }

    public String getToken(SystemUserBo user) {
        Instant now = Instant.now();
        Instant expire = Instant.now().plus(securityProperties.getTokenExpiration());
        return JWT
                .create()
                .withIssuer(securityProperties.getTokenIssuer())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expire))
                .withSubject(user.getUsername())
                .sign(algorithm);
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private JWTUserDetails getUserDetails(SystemUserBo user, String token) {
        JWTUserDetails jwtUserDetails = new JWTUserDetails();
        jwtUserDetails.setUsername(user.getUsername());
        jwtUserDetails.setPassword(user.getPassword());
        jwtUserDetails.setAuthorities(user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        jwtUserDetails.setToken(token);
        jwtUserDetails.setRoles(user.getRoles());
        return jwtUserDetails;
    }
}
