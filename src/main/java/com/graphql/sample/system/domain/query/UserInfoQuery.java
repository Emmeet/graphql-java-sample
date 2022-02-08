package com.graphql.sample.system.domain.query;

import com.graphql.sample.framework.entity.JsonParamDto;
import lombok.Data;

@Data
public class UserInfoQuery extends JsonParamDto {

    private String username;
}
