package com.graphql.sample;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureWebGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@SpringBootTest
@AutoConfigureWebGraphQlTester
public class SampleApplicationTests {

    @Autowired
    private WebGraphQlTester graphQlTester;

    public String token;

    @BeforeEach
    void loginTest() {
        token = graphQlTester.queryName("test/login")
                .execute()
                .path("login.username")
                .entity(String.class)
                .isEqualTo("admin")
                .path("login.token")
                .entity(String.class)
                .get();
    }
}