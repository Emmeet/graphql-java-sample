package com.graphql.sample.framework.entity;

import lombok.Data;

@Data
public class JsonParamDto {
    private long currentPage = 1L;

    private long pageSize = 99999L;
}
