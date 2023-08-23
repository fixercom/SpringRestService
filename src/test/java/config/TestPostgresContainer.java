package config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestPostgresContainer {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader("src/test/resources/datasource.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int CONTAINER_PORT = 5432;
    private static final int LOCAL_PORT = Integer.parseInt(properties.getProperty("dataSource.portNumber"));

    public static PostgreSQLContainer<?> getInstance() {
        return new PostgreSQLContainer<>("postgres:14-alpine")
                .withDatabaseName(properties.getProperty("dataSource.databaseName"))
                .withUsername(properties.getProperty("dataSource.user"))
                .withPassword(properties.getProperty("dataSource.password"))
                .withExposedPorts(CONTAINER_PORT)
                .withInitScript("schema.sql")
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                        new PortBinding(Ports.Binding.bindPort(LOCAL_PORT), new ExposedPort(CONTAINER_PORT)))));
    }
}
