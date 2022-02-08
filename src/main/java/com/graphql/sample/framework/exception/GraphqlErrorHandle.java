package com.graphql.sample.framework.exception;

import graphql.GraphqlErrorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

@Configuration
public class GraphqlErrorHandle {

    /**
     * 会覆盖掉原本的错误提示
     */
    @Bean
    public DataFetcherExceptionResolver exceptionResolver() {
        return DataFetcherExceptionResolverAdapter.from((ex, env) -> GraphqlErrorBuilder.newError(env)
                .message(ex.getMessage())
                .build()
        );
    }
}
