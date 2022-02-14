package com.graphql.sample.system;

import com.graphql.sample.SampleApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@Slf4j
@SpringBootTest
public class UserTest extends SampleApplicationTests {

    @Autowired
    private WebGraphQlTester graphQlTester;

    @Test
    void registerUser() {
        graphQlTester.queryName("test/register")
                .execute()
                .path("register.success")
                .entity(Boolean.class)
                .isEqualTo(Boolean.TRUE)
                .path("register.user.username")
                .entity(String.class)
                .isEqualTo("admin1");
    }

    @Test
    void queryUser() {
        graphQlTester.queryName("test/queryUsers")
                .httpHeader("Authorization", "Bearer " + token)
                .execute()
                .path("queryUsers[0].username")
                .entity(String.class)
                .isEqualTo("admin");
    }
}
