package com.graphql.sample.system.domain.bo;

import com.graphql.sample.system.domain.bean.SystemUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class SystemUserBo extends SystemUser {

    private static final long serialVersionUID = 1L;

    private List<String> roles = Arrays.asList("ROLE_ADMIN");
}