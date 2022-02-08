package com.graphql.sample.framework.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AjaxResponseBody {

    private Integer status;

    private Integer code;

    private String message;
}
