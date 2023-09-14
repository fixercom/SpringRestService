package com.example.config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.util.Properties;

/***
 * The singleton container is started only once when the base class is loaded.
 * The container can then be used by all inheriting test classes. At the end of
 * the test suite the Ryuk container that is started by Testcontainers core will
 * take care of stopping the singleton container.
 * **/
public abstract class AbstractPostgresContainer {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(AbstractPostgresContainer.class.getResourceAsStream("/datasource.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;
    private static final int CONTAINER_PORT = 5432;
    private static final int HOST_PORT = Integer.parseInt(properties.getProperty("dataSource.portNumber"));
    private static final PortBinding PORT_BINDING = new PortBinding(Ports.Binding.bindPort(HOST_PORT),
            new ExposedPort(CONTAINER_PORT));

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14-alpine")
                .withDatabaseName(properties.getProperty("dataSource.databaseName"))
                .withUsername(properties.getProperty("dataSource.user"))
                .withPassword(properties.getProperty("dataSource.password"))
                .withExposedPorts(CONTAINER_PORT)
                .withInitScript("schema.sql")
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig()
                        .withPortBindings(PORT_BINDING)));
        POSTGRESQL_CONTAINER.start();
    }

}
