package com.graphql.sample.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graphql.sample.system.domain.bean.SystemUser;
import com.graphql.sample.system.domain.bo.SystemUserBo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    Optional<SystemUserBo> querySystemUserByUsername(String userName);
}
