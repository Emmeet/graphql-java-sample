package com.graphql.sample.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import static com.graphql.sample.framework.scalar.DateTimeScalar.DATE_TIME;
import static com.graphql.sample.framework.scalar.EmailScalar.EMAIL;

@Configuration
public class GraphQlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(EMAIL)
                .scalar(DATE_TIME);
    }
}


