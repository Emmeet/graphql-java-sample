package com.graphql.sample.framework.scalar;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateTimeScalar {


    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final GraphQLScalarType DATE_TIME = GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("A custom scalar that handles date time")
            .coercing(new Coercing() {
                @Override
                public String serialize(Object o) {
                    LocalDateTime date = (LocalDateTime) o;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
                    return dtf.format(date);
                }

                @Override
                public LocalDateTime parseValue(Object o) {
                    String value = String.valueOf(o);
                    if ("null".equalsIgnoreCase(value) || "".equalsIgnoreCase(value)) {
                        return null;
                    }
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
                    return LocalDateTime.parse(value, dtf);
                }

                @Override
                public LocalDateTime parseLiteral(Object o) {
                    String value = String.valueOf(o);
                    if ("null".equalsIgnoreCase(value) || "".equalsIgnoreCase(value)) {
                        return null;
                    }
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
                    return LocalDateTime.parse(value, dtf);
                }
            })
            .build();
}
