package com.dio.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionConfig {
    
    public static Connection getConnection() throws SQLException{
        var connection = DriverManager.getConnection("jdbc:h2:mem:meubanco", "sa", "");

        connection.setAutoCommit(false);
        return connection;
    }
}
