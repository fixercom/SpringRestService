package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource2 {

    private static final HikariConfig CONFIG = new HikariConfig("/datasource.properties");
    private static final HikariDataSource HIKARI_DATA_SOURCE = new HikariDataSource(CONFIG);

    private DataSource2() {
    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }

}
