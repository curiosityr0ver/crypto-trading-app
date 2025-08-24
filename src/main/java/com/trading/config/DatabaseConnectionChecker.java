package com.trading.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseConnectionChecker {

    @Bean
    public CommandLineRunner checkDatabaseConnection(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                if (conn.isValid(2)) {  // timeout = 2 seconds
                    System.out.println("✅ Database connection established successfully.");
                } else {
                    System.err.println("⚠️ Database connection test failed (invalid connection).");
                }
            } catch (Exception e) {
                System.err.println("❌ Could not establish database connection: " + e.getMessage());
            }
        };
    }
}
