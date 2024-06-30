package org.lesson41.postgres.driver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PostgresDriverManager {
    @Value("${db.url}")
    private String URL;
    @Value("${db.username}")
    private String USERNAME;
    @Value("${db.password}")
    private String PASSWORD;

    @PostConstruct
    private void init() {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Exception loading driver...");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
