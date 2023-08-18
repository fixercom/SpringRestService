package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDataSource {

    private static final HikariConfig CONFIG = new HikariConfig("/datasource.properties");
    private static final HikariDataSource HIKARI_DATA_SOURCE = new HikariDataSource(CONFIG);

    private TestDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }

}
