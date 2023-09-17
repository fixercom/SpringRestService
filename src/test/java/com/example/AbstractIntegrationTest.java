package com.example;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractIntegrationTest {

    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14-alpine")
                    .withInitScript("schema.sql");

    static {
        container.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        System.out.println(container.getJdbcUrl());
        registry.add("hikari.jdbcUrl", container::getJdbcUrl);
        registry.add("hikari.user", container::getUsername);
        registry.add("hikari.password", container::getPassword);
    }

}
